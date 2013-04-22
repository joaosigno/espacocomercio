package net.danielfreire.products.ecommerce.model.core;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.danielfreire.products.ecommerce.model.domain.ClientAdmin;
import net.danielfreire.products.ecommerce.model.domain.Site;
import net.danielfreire.products.ecommerce.model.repository.ClientAdminRepository;
import net.danielfreire.products.ecommerce.model.repository.ClientEcommerceRepository;
import net.danielfreire.products.ecommerce.model.repository.SiteRepository;
import net.danielfreire.products.ecommerce.util.EcommerceUtil;
import net.danielfreire.products.ecommerce.util.GoogleUtil;
import net.danielfreire.util.ConvertTools;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.PortalTools;
import net.danielfreire.util.ValidateTools;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("siteBusiness")
public class SiteBusinessImpl implements SiteBusiness {

	private static final String LBL_NAME = "name";
	private static final String KEY_LOCATION_SITE = "location.generatesite";
	private static final String KEY_TITLE_SITE = "${title}";
	@Autowired
	private transient SiteRepository repository;
	@Autowired
	private transient ClientAdminRepository crepository;
	@Autowired
	private transient ClientEcommerceRepository clientRepository;

	@Override
	public GenericResponse load(final HttpServletRequest request) throws java.lang.Exception {
		final GenericResponse resp = new GenericResponse();
		final Site site = EcommerceUtil.getInstance().getSessionAdmin(request).getSite();
		resp.setGeneric(new Object[] { site , ConvertTools.getInstance().normalizeString(site.getName()) });
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GenericResponse save(final HttpServletRequest request) throws java.lang.Exception {
		GenericResponse resp = new GenericResponse();
		
		final String logo = request.getParameter("logo");
		final Map<String, Object> siteRet = getSite(request, true);
		final Map<String, String> error = (Map<String, String>) siteRet.get("error");
		
		if (error.isEmpty()) {
			Site site = (Site) siteRet.get("site");
			if (site.getId()==null) {
				site = repository.save(site);
				createInitUsers(site);
				generateFisicalSite(site, logo);
			}
		} else {
			resp = PortalTools.getInstance().getRespError(error);
		}
		
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public GenericResponse update(final HttpServletRequest request) throws java.lang.Exception {
		GenericResponse resp = new GenericResponse();
		
		final String logo = request.getParameter("logo");
		final String nameNormOther = ConvertTools.getInstance().normalizeString(EcommerceUtil.getInstance().getSessionAdmin(request).getSite().getName());
		final String nameNormalize = ConvertTools.getInstance().normalizeString(request.getParameter(LBL_NAME));
		final String extension = logo.substring(logo.lastIndexOf('.') + 1);
		final boolean changeLogo = ConvertTools.getInstance().convertBoolean(request.getParameter("changeLogo"));
		
		final String nameOther = EcommerceUtil.getInstance().getSessionAdmin(request).getSite().getName();
		final String descOther = EcommerceUtil.getInstance().getSessionAdmin(request).getSite().getDescription();
		final String faceOther = EcommerceUtil.getInstance().getSessionAdmin(request).getSite().getFacebook();
		final String logoOther = EcommerceUtil.getInstance().getSessionAdmin(request).getSite().getLogo();
		
		final Map<String, Object> siteRet = getSite(request, false);
		final Map<String, String> error = (Map<String, String>) siteRet.get("error");
		
		if (error.isEmpty()) {
			final StringBuilder dirTo = new StringBuilder()
				.append(PortalTools.getInstance().getEcommerceProperties(KEY_LOCATION_SITE))
				.append("/")
				.append(nameNormalize)
				.append("/");
			
			final Site site = (Site) siteRet.get("site");
			repository.save(site);
			renameSite(nameNormOther, nameNormalize);
			changeReferences(nameOther, descOther, faceOther, logoOther, site, nameNormalize, nameNormOther, dirTo.toString());
			if (changeLogo) {
				changeLogo(logo, extension, dirTo.toString());
			}
			updateGmailPass(site);
		} else {
			resp = PortalTools.getInstance().getRespError(error);
		}
		
		return resp;
	}
	
	private void updateGmailPass(final Site site) throws java.lang.Exception {
		new GoogleUtil().updatePassword(site);
	}

	private Map<String, Object> getSite(final HttpServletRequest request, final boolean isInsert) {
		final String name = request.getParameter(LBL_NAME);
		final String description = request.getParameter("description");
		final String facebook = request.getParameter("facebook");
		final String logo = request.getParameter("logo");
		final String email = request.getParameter("email");
		final String gmailpass = request.getParameter("gmailpass");
		
		final String nameNormalize = ConvertTools.getInstance().normalizeString(name);
		final String extension = logo.substring(logo.lastIndexOf('.') + 1);
		
		Site site = null;
		if (isInsert) {
			final String idSite = request.getParameter("id");
			site = new Site();
			if (ValidateTools.getInstancia().isNumber(idSite)) {
				site.setIdSite(Integer.parseInt(idSite));
			}
			site.setGmailPass(PortalTools.PASS_AUTH_PATTERN);
		} else {
			site = EcommerceUtil.getInstance().getSessionAdmin(request).getSite();
			site.setGmailPass(gmailpass);
		}
		
		site.setDescription(description);
		site.setName(name);
		site.setFacebook(facebook);
		site.setLogo("/ecommerce/"+nameNormalize+"/logo."+extension);
		site.setEmail(email);
		
		final Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("site", site);
		ret.put("error", validateSite(site));
		
		return ret;
	}
	
	private void changeLogo(final String logo, final String extension, final String dirTo) throws IOException {
		final String photoFile = PortalTools.getInstance().getEcommerceProperties(KEY_LOCATION_SITE)+"/temp/"+logo;
		final BufferedImage img = ImageIO.read(new File(photoFile));
		final BufferedImage bufferedI = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		final Graphics2D grph = (Graphics2D) bufferedI.getGraphics();
		grph.drawImage(img, 0, 0, null);
		grph.dispose();
		final File logoTipo = new File(dirTo + "/logo." + extension);
		if (logoTipo.isFile()) {
			logoTipo.delete();
		}
		ImageIO.write(bufferedI, extension, logoTipo);
	}

	private void changeReferences(final String nameOther, final String descOther, final String faceOther, final String logoOther, final Site siteNew, final String nameNormalize, final String nameNormaOther, final String dirTo) throws java.lang.Exception {
 		final Map<String, String> mapHome = new HashMap<String, String>();
		mapHome.put(nameOther, siteNew.getName());
		if (ValidateTools.getInstancia().isNullEmpty(faceOther) && ValidateTools.getInstancia().isNotnull(siteNew.getFacebook())) {
			mapHome.put("<!--ReservedToFacebook-->", "<div class=\"fb-like\" data-href=\""+siteNew.getFacebook()+"\" data-send=\"true\" data-layout=\"button_count\" data-show-faces=\"true\"></div><br><br>");
			mapHome.put("<!--ReservedToFacebookConf-->", "<div id=\"fb-root\"></div><script>(function(d, s, id) {var js, fjs = d.getElementsByTagName(s)[0];if (d.getElementById(id)) return;js = d.createElement(s); js.id = id;js.src = \"//connect.facebook.net/en_US/all.js#xfbml=1\"; fjs.parentNode.insertBefore(js, fjs);}(document, 'script', 'facebook-jssdk'));</script>");
		} else if (ValidateTools.getInstancia().isNotnull(faceOther) && ValidateTools.getInstancia().isNotnull(siteNew.getFacebook())) {
			mapHome.put(faceOther, siteNew.getFacebook());
		} else {
			mapHome.put("<div class=\"fb-like\" data-href=\""+faceOther+"\" data-send=\"true\" data-layout=\"button_count\" data-show-faces=\"true\"></div><br><br>", "<!--ReservedToFacebook-->");
			mapHome.put("<div id=\"fb-root\"></div><script>(function(d, s, id) {var js, fjs = d.getElementsByTagName(s)[0];if (d.getElementById(id)) return;js = d.createElement(s); js.id = id;js.src = \"//connect.facebook.net/en_US/all.js#xfbml=1\"; fjs.parentNode.insertBefore(js, fjs);}(document, 'script', 'facebook-jssdk'));</script>", "<!--ReservedToFacebookConf-->");
		}
		ConvertTools.getInstance().replaceFile(mapHome, new File(dirTo + "/index.html"));
		
		final Map<String, String> mapFunctions = new HashMap<String, String>();
		mapFunctions.put(nameOther + " " + descOther, siteNew.getName() + " " + siteNew.getDescription());
		mapFunctions.put(nameNormaOther, nameNormalize);
		mapFunctions.put(logoOther, siteNew.getLogo());
		ConvertTools.getInstance().replaceFile(mapFunctions, new File(dirTo+"/js/functions.js"));
		
		final Map<String, String> mapCategory = new HashMap<String, String>();
		mapCategory.put(nameOther + " - Categoria", siteNew.getName() + " - Categoria");
		ConvertTools.getInstance().replaceFile(mapCategory, new File(dirTo+"/category/index.html"));
		
		final Map<String, String> mapClient = new HashMap<String, String>();
		mapClient.put(nameOther + " - Painel de controle", siteNew.getName() + " - Painel de controle");
		ConvertTools.getInstance().replaceFile(mapClient, new File(dirTo+"/client/index.html"));
		
		final Map<String, String> mapMycart = new HashMap<String, String>();
		mapMycart.put(nameOther + " - Meu carrinho", siteNew.getName() + " - Meu carrinho");
		ConvertTools.getInstance().replaceFile(mapMycart, new File(dirTo+"/mycart/index.html"));
		
		final Map<String, String> mapProduct = new HashMap<String, String>();
		mapProduct.put(nameOther + " - Produto", siteNew.getName() + " - Produto");
		ConvertTools.getInstance().replaceFile(mapProduct, new File(dirTo+"/product/index.html"));
	}

	private void renameSite(final String nameNormaOther, final String nameNormalize) {
		final String sitePath = PortalTools.getInstance().getEcommerceProperties(KEY_LOCATION_SITE) + "/";
		final File otherSite = new File(sitePath + nameNormaOther);
		final File newSite = new File(sitePath + nameNormalize);
		
		otherSite.renameTo(newSite);
	}

	private void createInitUsers(final Site site) throws java.lang.Exception {
		ClientAdmin user = new ClientAdmin();
		user.setPassword("dabf1985");
		user.setPermission(1);
		user.setSite(site);
		user.setUser("daniel@danielfreire.net");
		crepository.save(user);
		
		user = new ClientAdmin();
		user.setPassword("mudar1234");
		user.setPermission(2);
		user.setSite(site);
		user.setUser(site.getEmail());
		crepository.save(user);
		
		new GoogleUtil().createGoogleAccount(site.getName(), ConvertTools.getInstance().normalizeString(site.getName()));
	}

	private Map<String, String> validateSite(final Site site) {
		final HashMap<String, String> error = new HashMap<String, String>();
		
		error.putAll(ValidateTools.getInstancia().validateGeneric("name.invalid", ValidateTools.getInstancia().isNullEmpty(site.getName())));
		error.putAll(ValidateTools.getInstancia().validateGeneric("facebook.invalid", !ValidateTools.getInstancia().isNullEmpty(site.getFacebook()) && !ValidateTools.getInstancia().isUrl(site.getFacebook())));
		error.putAll(ValidateTools.getInstancia().validateGeneric("logo.invalid", ValidateTools.getInstancia().isNullEmpty(site.getLogo())));
		error.putAll(ValidateTools.getInstancia().validateGeneric("email.invalid", !ValidateTools.getInstancia().isEmail(site.getEmail())));
		error.putAll(ValidateTools.getInstancia().validateGeneric("gmailpass.invalid", !ValidateTools.getInstancia().isPassword(site.getGmailPass())));
		
		if (error.isEmpty()) {
			final String[] blackList = PortalTools.getInstance().getEcommerceProperties("blacklist.site.name").split(",");
			for (String black : blackList) {
				error.putAll(ValidateTools.getInstancia().validateGeneric("name.notavaiable", ConvertTools.getInstance().normalizeString(site.getName()).equals(black)));
			}
		}
		
		if (error.isEmpty() && ValidateTools.getInstancia().isNullEmpty(site.getId().toString())) {
			final List<Site> list = repository.findAll();
			for (Site siteExist : list) {
				error.putAll(ValidateTools.getInstancia().validateGeneric("name.notavaiable", ConvertTools.getInstance().normalizeString(siteExist.getName()).equals(ConvertTools.getInstance().normalizeString(site.getName()))));
			}
		}
		
		return error;
	}

	@Override
	public void upload(final HttpServletRequest request, final HttpServletResponse response)
			throws java.lang.Exception {
		
		final boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		if (isMultipart) {
			final FileItemFactory factory = new DiskFileItemFactory();
			final ServletFileUpload upload = new ServletFileUpload(factory);
			final List<?> items = upload.parseRequest(request);
			final Iterator<?> itr = items.iterator();
			
			while (itr.hasNext()) {
				final FileItem item = (FileItem) itr.next();
				if (!item.isFormField()){
					final String itemName = item.getName();
					
					final int IndexOf = itemName.indexOf('.');
					final String domainName = itemName.substring(IndexOf);
					
					if (domainName.equalsIgnoreCase(".jpg") || domainName.equalsIgnoreCase(".png") || domainName.equalsIgnoreCase(".jpeg") ) {
						final String finalimage = generateGenericName()+domainName;
						
						final String photoFile = PortalTools.getInstance().getEcommerceProperties(KEY_LOCATION_SITE)+
								"/temp/"+finalimage;
						
						final File savedFile = generateGenericFile(photoFile);
						item.write(savedFile);
						
						final String reponseText = "uploadSetLogo('"+finalimage+"');";
						
						response.getWriter().print(reponseText);
					} else {
						response.getWriter().print("$('#alertError').val('"+PortalTools.getInstance().getMessage("file.invalid")+"');$('#alertError').click()");
					}
				}
			}
		} else {
			response.getWriter().print("$('#alertError').val('"+PortalTools.getInstance().getMessage("upload.invalid")+"');$('#alertError').click()");
	  	}
		
	}
	
	
	private File generateGenericFile(final String photoFile) {
		return new File(photoFile);
	}

	private String generateGenericName() {
		return String.valueOf(new Date().getTime());
	}

	private void generateFisicalSite(final Site site, final String logoOrigin) throws java.lang.Exception {
		final String nameNormalize = ConvertTools.getInstance().normalizeString(site.getName());
		final String dirTo = PortalTools.getInstance().getEcommerceProperties(KEY_LOCATION_SITE) + "/" + nameNormalize;
		
		final File fileTo = new File(dirTo);
		if (fileTo.exists()) {
			fileTo.delete();
			fileTo.mkdir();
		} else {
			fileTo.mkdir();
		}
		
		PortalTools.getInstance().copyDirectory(new File(PortalTools.getInstance().getEcommerceProperties("location.patternsite")), new File(dirTo));

		final Map<String, String> mapHome = new HashMap<String, String>();
		mapHome.put(KEY_TITLE_SITE, site.getName());
		if (ValidateTools.getInstancia().isNullEmpty(site.getFacebook())) {
			mapHome.put("${facebook-like}", "<!--ReservedToFacebook-->");
			mapHome.put("${facebook-conf}", "<!--ReservedToFacebookConf-->");
		} else {
			mapHome.put("${facebook-like}", "<div class=\"fb-like\" data-href=\""+site.getFacebook()+"\" data-send=\"true\" data-layout=\"button_count\" data-show-faces=\"true\"></div><br><br>");
			mapHome.put("${facebook-conf}", "<div id=\"fb-root\"></div><script>(function(d, s, id) {var js, fjs = d.getElementsByTagName(s)[0];if (d.getElementById(id)) return;js = d.createElement(s); js.id = id;js.src = \"//connect.facebook.net/en_US/all.js#xfbml=1\"; fjs.parentNode.insertBefore(js, fjs);}(document, 'script', 'facebook-jssdk'));</script>");
		}
		ConvertTools.getInstance().replaceFile(mapHome, new File(dirTo+"/index.html"));
		
		final Map<String, String> mapFunctions = new HashMap<String, String>();
		mapFunctions.put("${altBanner}", site.getName() + " " + site.getDescription());
		mapFunctions.put("${urlHome}", "/ecommerce/" + nameNormalize);
		mapFunctions.put("${sid}", PortalTools.getInstance().encode(site.getId().toString()));
		mapFunctions.put("${context}", nameNormalize);
		mapFunctions.put("${logo}", site.getLogo());
		ConvertTools.getInstance().replaceFile(mapFunctions, new File(dirTo+"/js/functions.js"));
		
		final Map<String, String> mapCategory = new HashMap<String, String>();
		mapCategory.put(KEY_TITLE_SITE, site.getName() + " - Categoria");
		ConvertTools.getInstance().replaceFile(mapCategory, new File(dirTo+"/category/index.html"));
		
		final Map<String, String> mapClient = new HashMap<String, String>();
		mapClient.put(KEY_TITLE_SITE, site.getName() + " - Painel de controle");
		ConvertTools.getInstance().replaceFile(mapClient, new File(dirTo+"/client/index.html"));
		
		final Map<String, String> mapMycart = new HashMap<String, String>();
		mapMycart.put(KEY_TITLE_SITE, site.getName() + " - Meu carrinho");
		ConvertTools.getInstance().replaceFile(mapMycart, new File(dirTo+"/mycart/index.html"));
		
		final Map<String, String> mapProduct = new HashMap<String, String>();
		mapProduct.put(KEY_TITLE_SITE, site.getName() + " - Produto");
		ConvertTools.getInstance().replaceFile(mapProduct, new File(dirTo+"/product/index.html"));
		
		final String extension = logoOrigin.substring(logoOrigin.lastIndexOf('.') + 1);
		changeLogo(logoOrigin, extension, dirTo);
	}

	@Override
	public Long countTotalSiteBySessionAdmin(final HttpServletRequest request) throws java.lang.Exception {
		return clientRepository.countBySite(EcommerceUtil.getInstance().getSessionAdmin(request).getSite());
	}

	@Override
	public String getNormalizeNameSiteBySessionAdmin(final HttpServletRequest request) {
		return ConvertTools.getInstance().normalizeString(EcommerceUtil.getInstance().getSessionAdmin(request).getSite().getName());
	}

}

