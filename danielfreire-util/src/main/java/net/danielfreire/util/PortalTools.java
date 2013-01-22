package net.danielfreire.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class PortalTools {
	
	private static PortalTools tools = new PortalTools();
	
	public PortalTools() {
		super();
	}
	
	public static synchronized PortalTools getInstance() {
        return tools;
    }
	
	/** Messages Bundle */
	private ResourceBundle messagesBundle;
	
	/** Messages Bundle */
	private ResourceBundle propertiesEcommerceBundle;
	
	/** Id Session */
	public final String idSession = "userAuthenticated";
	
	/** Id Session */
	public final String idAdminSession = "userAdminAuthenticated";
	
	/** Id Session */
	public final String idAdminMasterSession = "userAdminMasterAuthenticated";
	
	/** Id Session */
	public final String idCartSession = "cartSession";
	
	/** Data padrao - dd/MM/yyyy **/
	private SimpleDateFormat sdf_padrao;
	
	/** Data banco de dados - yyyy/MM/dd HH:mm:ss **/
	private SimpleDateFormat sdf_db;
	
	private final Logger logger = Logger.getLogger(PortalTools.class.getName()); 
	
	
	/**
	 * Return the value of the key in the resource bundle or return the key
	 * itself
	 * 
	 * @param key
	 *            the key of the message
	 * @return the message
	 */
	public String getMessage(String key) {
		return getMessage(key, new Object[0]);
	}

	/**
	 * Return the value of the key in the resource bundle or return the key
	 * itself
	 * 
	 * @param key
	 *            the key of the message
	 * @param arguments
	 *            the arguments of the message
	 * @return the message
	 */
	public String getMessage(String key, Object... arguments) {
		String returnValue = key;

		if (messagesBundle == null) {
			messagesBundle = ResourceBundle.getBundle("/messages");
		}

		if (key != null && messagesBundle.containsKey(key)) {
			returnValue = MessageFormat.format(messagesBundle.getString(key),
				arguments);
		}

		return returnValue;
	}
	
	/**
	 * Return the value of the key in the resource bundle or return the key
	 * itself
	 * 
	 * @param key
	 *            the key of the message
	 * @return the message
	 */
	public String getEcommerceProperties(String key) {
		return getEcommerceProperties(key, new Object[0]);
	}

	/**
	 * Return the value of the key in the resource bundle or return the key
	 * itself
	 * 
	 * @param key
	 *            the key of the message
	 * @param arguments
	 *            the arguments of the message
	 * @return the message
	 */
	public String getEcommerceProperties(String key, Object... arguments) {
		String returnValue = key;

		if (propertiesEcommerceBundle == null) {
			propertiesEcommerceBundle = ResourceBundle.getBundle("/ecommerce");
		}

		if (key != null && propertiesEcommerceBundle.containsKey(key)) {
			returnValue = MessageFormat.format(propertiesEcommerceBundle.getString(key),
				arguments);
		}

		return returnValue;
	}
	
	/**
	 * Return the value of the key in the resource bundle in ticket.properties
	 * 
	 * @param key
	 *            the key of the message
	 * @return the message
	 */
	private GenericResponse getRespError(HashMap<String, String> message, Boolean status) {
		GenericResponse resp = new GenericResponse();
		resp.setMessageError(message);
		resp.setStatus(status);
		
		return resp;
	}
	
	/**
	 * Return the value of the key in the resource bundle in ticket.properties
	 * 
	 * @param key
	 *            the key of the message
	 * @return the message
	 */
	public GenericResponse getRespError(String idMessage) {
		HashMap<String, String> error = new HashMap<String, String>();
		if (idMessage!="session.invalid") {
			error.put("generic", getMessage(idMessage.toString()));
		} else {
			error.put("sessionInvalid", getMessage(idMessage.toString()));
		}
		return getRespError(error, false);
	}
	
	public GenericResponse getRespError(HashMap<String, String> errors) {
		return getRespError(errors, false);
	}
	
	/**
	 * Return the value of the key in the resource bundle in ticket.properties
	 * 
	 * @param key
	 *            the key of the message
	 * @return the message
	 */
	public GenericResponse getRespError(Exception e) {
		logger.log(Level.SEVERE, e.getMessage(), e);
		return getRespError("system.out");
	}
	
	
	public SimpleDateFormat getDataPadrao() {
		if (sdf_padrao == null) {
			try {
				sdf_padrao = new SimpleDateFormat("dd/MM/yyyy");
			} catch (Exception e) {
				sdf_padrao = null;
				logger.log(Level.WARNING, e.getMessage(), e);
			}
		}
		
		return sdf_padrao;
	}
	
	public SimpleDateFormat getDataBD() {
		if (sdf_db == null) {
			try {
				sdf_db = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			} catch (Exception e) {
				sdf_db = null;
				logger.log(Level.WARNING, e.getMessage(), e);
			}
		}
		
		return sdf_db;
	}
	
	/**
	 * Encode Base64
	 * 
	 * @param code
	 * @return String
	 */
	@SuppressWarnings("restriction")
	public String Encode(String code) {
		return new sun.misc.BASE64Encoder().encode(code.getBytes());
	}

	/**
	 * Decode Base64
	 * 
	 * @param code
	 * @return String
	 */
	@SuppressWarnings("restriction")
	public String Decode(String code) {
		try {
			return new String(new sun.misc.BASE64Decoder().decodeBuffer(code));
		} catch (IOException e) {
			return "";
		}
	}
	
	/**
	 * This method validates that sent the token is valid
	 * 
	 * @param token
	 * @return boolean
	 */
	public boolean validToken(String token) {
		try {
			String decode = PortalTools.getInstance().Decode(token);
			String[] split = decode.split(" ");
	
			String[] dateSplit = split[1].split("-");
			String[] hourSplit = split[2].split("-");
	
			Calendar now = Calendar.getInstance();
			Calendar nowDecrypt = Calendar.getInstance();
			nowDecrypt.set(Integer.parseInt(dateSplit[2])/187, Integer.parseInt(dateSplit[1].substring(2, 4)), Integer.parseInt(dateSplit[0].substring(2, 4)), Integer.parseInt(hourSplit[0].substring(2, 4)), Integer.parseInt(hourSplit[1].substring(2, 4)));
	
			String decryptBase = "danielFreire"+(Integer.parseInt(dateSplit[0].substring(2, 4)) * Integer.parseInt(dateSplit[2])/187);
	
			if (decryptBase.equals(split[0]) && nowDecrypt.getTime().after(now.getTime())) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, e.getMessage(), e);
			return false;
		}
	}

	/**
	 * This method generates the following rule as Token
	 * 	Rule: [ticketEdenred][(DATA_VALIDA.DIA) * (DATA_VALIDA.ANO)] [DATA_ATUAL.HORA(00)][DATA_VALIDA.DIA(00)]-[DATA_ATUAL.MIN(00)][DATA_VALIDA.MES(00)]-[DATA_VALIDA.ANO(0000)*187] [DATA_ATUAL.DIA(00)][DATA_VALIDA.HORA(00)]-[DATA_ATUAL.MES(00)][DATA_VALIDA.MIN(00)]
	 * 
	 * @return String
	 */
	public String getToken() {
		DecimalFormat df = new DecimalFormat("00");

		Calendar here = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, 10);

		String keyBase = "danielFreire" + (now.get(Calendar.DAY_OF_MONTH) * now.get(Calendar.YEAR)); 

		StringBuilder date = new StringBuilder();
		date
		.append(df.format(here.get(Calendar.HOUR)))
		.append(df.format(now.get(Calendar.DAY_OF_MONTH)))
		.append("-")
		.append(df.format(here.get(Calendar.MINUTE)))
		.append(df.format(now.get(Calendar.MONTH)))
		.append("-")
		.append(now.get(Calendar.YEAR)*187);

		StringBuilder hour = new StringBuilder();
		hour
		.append(df.format(here.get(Calendar.DAY_OF_MONTH)))
		.append(df.format(now.get(Calendar.HOUR_OF_DAY)))
		.append("-")
		.append(df.format(here.get(Calendar.MONTH)))
		.append(df.format(now.get(Calendar.MINUTE)));

		StringBuilder code = new StringBuilder()
		.append(keyBase)
		.append(" " + date.toString())
		.append(" " + hour.toString());

		return PortalTools.getInstance().Encode(code.toString());
	}
	
	public byte[] resizeImage(byte[] imgByte, String imageType, int newW, int newH) throws IOException {  
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(imgByte));  
                  
        int w = img.getWidth();    
        int h = img.getHeight();    
        BufferedImage dimg = new BufferedImage(newW, newH, img.getType());    
        Graphics2D g = dimg.createGraphics();    
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);    
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);    
        g.dispose();  
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();  
        ImageIO.write(dimg, imageType, buffer);
        
        return buffer.toByteArray();  
    } 
	
	public GridTitleResponse getRowGrid(String id, String title, String type) {
		GridTitleResponse grid = new GridTitleResponse();
		grid.setId(id);
		grid.setTitle(title);
		grid.setType(type);
		
		return grid;
	}
	
	public GridResponse getGrid(List<?> content, ArrayList<GridTitleResponse> titles, Integer pagination, Integer totalPage) {
		GridResponse grid = new GridResponse();
		grid.setRows(content);
		grid.setTitles(titles);
		grid.setPage(pagination);
		grid.setTotalPages(totalPage);
		
		return grid;
	}

}
