package net.danielfreire.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

public class ConvertTools {
	
	private static ConvertTools convert = new ConvertTools();
	
	public ConvertTools() {
		super();
	}
	
	public static synchronized ConvertTools getInstance() {
        return convert;
    }
	
	public final String LABEL_SEPARATOR_STRING = "---";
	
	/**   
     *  This method print CPF on mask (###.###.###-##)
     *  
     *  @param java.lang.String
     */ 
	public String imprimeCPF(String CPF) {
	    return(CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." +
	      CPF.substring(6, 9) + "-" + CPF.substring(9, 11));
	}
	
	public String addUrlParameter(String url, String paramName, String paramValue) {
		if (url.contains("?")) {
			url = url + "&" + paramName + "=" + paramValue;
		} else {
			url = url + "?" + paramName + "=" + paramValue;
		}
		
		return url;
	}
	
	public String getUrlParameter(String url, String paramName) {
		try {
			if (url.contains("?")) {
				url = url.split("?")[1];
			}
			String[] urlSplit = url.split("&");
			HashMap<String, String> map = new HashMap<String, String>(); 
			for (String u : urlSplit) {
				String[] m = u.split("=");
				
				String exists = map.get(m[0]);
				if (ValidateTools.getInstancia().isNullEmpty(exists)) {
					if (m.length==2) {
						map.put(m[0], m[1]);
					} else {
						map.put(m[0], "");
					}
					
				} else {
					if (m.length==2) {
						map.put(m[0], exists + LABEL_SEPARATOR_STRING + m[1]);
					} else {
						map.put(m[0], "");
					}
				}
			}
			
			return map.get(paramName);
		} catch (Exception e) {
			//Logger.getLogger(ConvertTools.class).error(e);
		}
		
		return null;
	}
	
	/**   
     *  This method convert value (###.###,##) to Double
     *  
     *  @param java.lang.Double
     */ 
	public Double convertMoeda(String value) {
		try {
			if (ValidateTools.getInstancia().isNullEmpty(value)) {
				return new Double(0.0);
			} else {
				value = value.replace(".", "");
				value = value.replace(",", ".");
				
				return new Double(value);
			}
		} catch (Exception e) {
			return new Double(0.0);
		}
	}
	
	/**   
     *  This method convert value (BigDecimal) to String value
     *  
     *  @param java.lang.Double
     */ 
	public String convertMoeda(BigDecimal value) {
		return convertMoeda(value.doubleValue());
	}
	
	/**   
     *  This method convert value (Double) to String value
     *  
     *  @param java.lang.Double
     */ 
	public String convertMoeda(Double value) {
		NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));     
		moneyFormat.setMinimumFractionDigits(2);
		try {
			return moneyFormat.format(value);
		} catch (Exception e) {
			return value.toString();
		}
	}
	
	/**   
     *  This method convert value (Double) to String value
     *  
     *  @param java.lang.Double
     */ 
	public String convertPercentage(BigDecimal value) {
		DecimalFormat df = new DecimalFormat("##,###.00");
		try {
			return df.format(value).replace(".", ",").concat("%");
		} catch (Exception e) {
			return value.toString();
		}
	}
	
	/**   
     *  This method convert Long to String
     *  
     *  @param java.lang.Double
     */ 
	public String toString(Long value) {
		try {
			return value.toString();
		} catch (Exception e) {
			return "0";
		}
	}
	
	/**   
     *  This method convert Integer to String
     *  
     *  @param java.lang.Integer
     */ 
	public String toString(Integer value) {
		try {
			return value.toString();
		} catch (Exception e) {
			return "0";
		}
	}
	
	public String preencheCom(String linha_a_preencher, String letra, int tamanho, int direcao){
        if (linha_a_preencher == null || linha_a_preencher.trim() == "" ) {
        	linha_a_preencher = "";
        }

        while (linha_a_preencher.contains(" ")) {
        	linha_a_preencher = linha_a_preencher.replaceAll(" "," ").trim();
        }

        linha_a_preencher = linha_a_preencher.replaceAll("[./-]","");
        StringBuffer sb = new StringBuffer(linha_a_preencher);

        if (direcao==1){ //a Esquerda
            for (int i=sb.length() ; i<tamanho ; i++){
                sb.insert(0,letra);
            }
        } else if (direcao==2) {//a Direita
            for (int i=sb.length() ; i<tamanho ; i++){
                sb.append(letra);
            }
        }

        return sb.toString();

    }
	
	public String convertBoolean(Boolean v) {
		try {
			if (v) {
				return "1";
			} else {
				return "0";
			}
		} catch (NullPointerException e) {
			return "0";
		}
	}
	
	public boolean convertBoolean(String v) {
		try {
			if (v.equals("1") || Boolean.valueOf(v)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	public String convertNullString(String v) {
		if (v==null) {
			return "null";
		} else {
			return "'"+v+"'";
		}
	}
	
	public String convertNullDate(String date) {
		try {
			if (date!=null) {
				try {
					return "'"+new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(date))+"'";
				} catch (ParseException e) {
					return "null";
				}
			} else {
				return "null";
			}
		} catch (NullPointerException e) {
			return "null";
		}
	}
	
	public String normalizeString(String texto) {
		texto = texto.toLowerCase();
		
		texto = texto.replace(".", "-");
		texto = texto.replace(",", "-");
		texto = texto.replace(";", "-");
		texto = texto.replace(":", "-");
		texto = texto.replace(" ", "-");
		texto = texto.replace("~", "-");
		texto = texto.replace("^", "-");
		texto = texto.replace("]", "-");
		texto = texto.replace("}", "-");
		texto = texto.replace("[", "-");
		texto = texto.replace("{", "-");
		texto = texto.replace("'", "-");
		texto = texto.replace("`", "-");
		texto = texto.replace("=", "-");
		texto = texto.replace("+", "-");
		texto = texto.replace("_", "-");
		texto = texto.replace(")", "-");
		texto = texto.replace("(", "-");
		texto = texto.replace("*", "-");
		texto = texto.replace("&", "-");
		texto = texto.replace("%", "-");
		texto = texto.replace("$", "-");
		texto = texto.replace("#", "-");
		texto = texto.replace("@", "-");
		texto = texto.replace("!", "-");
		texto = texto.replace("'", "-");
		texto = texto.replace("\"", "-");
		texto = texto.replace("\\", "-");
		texto = texto.replace("|", "-");
		texto = texto.replace("/", "-");
		texto = texto.replace("<", "-");
		texto = texto.replace(">", "-");
		texto = texto.replace("?", "-");
		
		texto = texto.replace("ã", "a");
		texto = texto.replace("á", "a");
		texto = texto.replace("â", "a");
		texto = texto.replace("à", "a");
		
		texto = texto.replace("ẽ", "e");
		texto = texto.replace("é", "e");
		texto = texto.replace("ê", "e");
		texto = texto.replace("è", "e");
		
		texto = texto.replace("ĩ", "i");
		texto = texto.replace("í", "i");
		texto = texto.replace("î", "i");
		texto = texto.replace("ì", "i");
		
		texto = texto.replace("õ", "o");
		texto = texto.replace("ó", "o");
		texto = texto.replace("ô", "o");
		texto = texto.replace("ò", "o");
		
		texto = texto.replace("ũ", "u");
		texto = texto.replace("ú", "u");
		texto = texto.replace("û", "u");
		texto = texto.replace("ù", "u");
		
		texto = texto.replace("ç", "c");
		
		return texto;
	}

	public String normalizeEspecialChar(String texto) {
		texto = texto.replace("ã", "&atilde;");
		texto = texto.replace("á", "&aacute;");
		texto = texto.replace("â", "&acirc;");
		texto = texto.replace("à", "&agrave;");
		
		texto = texto.replace("ẽ", "&etilde;");
		texto = texto.replace("é", "&eacute;");
		texto = texto.replace("ê", "&ecirc;");
		texto = texto.replace("è", "&egrave;");
		
		texto = texto.replace("ĩ", "&itilde;");
		texto = texto.replace("í", "&iacute;");
		texto = texto.replace("î", "&icirc;");
		texto = texto.replace("ì", "&igrave;");
		
		texto = texto.replace("õ", "&otilde;");
		texto = texto.replace("ó", "&oacute;");
		texto = texto.replace("ô", "&ocirc;");
		texto = texto.replace("ò", "&ograve;");
		
		texto = texto.replace("ũ", "&utilde;");
		texto = texto.replace("ú", "&uacute;");
		texto = texto.replace("û", "&ucirc;");
		texto = texto.replace("ù", "&ugrave;");
		
		texto = texto.replace("ç", "&ccedil;");
		
		return texto;
	}
	
	public Double trunc(Double value, Integer decimais) {
		Double p = Math.pow(10, decimais);
		return Math.floor(value * p) / p;
	}

	public Double round(Double value, Integer decimais) {
		Double p = Math.pow(10, decimais);
		return Math.round(value * p) / p;
	}
	
	public List<String> converterFile(File file) throws IOException{    
		List<String> list = new ArrayList<String>();    
		String row = null;    
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));    
          
		while ((row = reader.readLine()) != null) {     
			list.add(row);    
		}    
		
		reader.close();
          
		return list;    
	} 
	
	public void replaceFile(Map<String, String> map, File file) throws Exception {
		List<String> content = ConvertTools.getInstance().converterFile(file);
		
		FileWriter fileWriter = new FileWriter(file, false);
		PrintWriter printWriter = new PrintWriter(fileWriter);
		
		Set<String> key = map.keySet();
		for (String l:content) {
			String nLine = l;
			for (String k : key) {
				nLine = nLine.replace(k, map.get(k));
			}
			printWriter.println(nLine);
		}
		
		printWriter.flush();
		printWriter.close();
	}
	
	public BufferedImage RedimensionImage(String caminho ,int w, int h) throws IOException {  
		BufferedImage fundo = null, image = null;  
	    fundo = ImageIO.read(new File(caminho));  
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);  
        Graphics g = image.getGraphics();  
        g.drawImage(fundo.getScaledInstance(w,h,10000), 0, 0, null);
        
        return image;  
   }
}