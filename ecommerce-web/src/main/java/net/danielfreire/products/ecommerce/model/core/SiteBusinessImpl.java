package net.danielfreire.products.ecommerce.model.core;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
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
import net.danielfreire.products.ecommerce.model.repository.SiteRepository;
import net.danielfreire.products.ecommerce.util.EcommerceUtil;
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

	@Autowired
	private SiteRepository repository;
	@Autowired
	private ClientAdminRepository crepository;

	@Override
	public GenericResponse load(HttpServletRequest request) throws Exception {
		GenericResponse resp = new GenericResponse();
		resp.setGeneric(new Object[] { EcommerceUtil.getInstance().getSessionAdmin(request).getSite() , ConvertTools.getInstance().normalizeString(EcommerceUtil.getInstance().getSessionAdmin(request).getSite().getName()) });
		return resp;
	}

	@Override
	public GenericResponse save(HttpServletRequest request) throws Exception {
		GenericResponse resp = new GenericResponse();
		
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String facebook = request.getParameter("facebook");
		String logo = request.getParameter("logo");
		String nameNormalize = ConvertTools.getInstance().normalizeString(name);
		
		HashMap<String, String> error = new HashMap<String, String>();
		if (ValidateTools.getInstancia().isNullEmpty(name)) {
			error.put("name", PortalTools.getInstance().getMessage("name.invalid"));
		}
		if (!ValidateTools.getInstancia().isNullEmpty(facebook) && !ValidateTools.getInstancia().isUrl(facebook)) {
			error.put("facebook", PortalTools.getInstance().getMessage("facebook.invalid"));
		}
		if (ValidateTools.getInstancia().isNullEmpty(logo)) {
			error.put("logo", PortalTools.getInstance().getMessage("logo.invalid"));
		}
		if (nameNormalize.equals("temp") || nameNormalize.equals("portal") || nameNormalize.equals("js") || nameNormalize.equals("patternsite") || nameNormalize.equals("img") || nameNormalize.equals("css") || nameNormalize.equals("admin")) {
			error.put("name", PortalTools.getInstance().getMessage("name.notavaiable"));
		}
		
		if (error.size()==0) {
			List<Site> list = repository.findAll();
			for (Site site : list) {
				if (ConvertTools.getInstance().normalizeString(site.getName()).equals(nameNormalize)) {
					error.put("name", PortalTools.getInstance().getMessage("name.notavaiable"));
					break;
				}
			}
		}
		
		if (error.size()==0) {
			if (ValidateTools.getInstancia().isNullEmpty(id)) {
				final String extension = logo.substring(logo.lastIndexOf(".") + 1);
				
				Site site = new Site();
				site.setDescription(description);
				site.setName(name);
				site.setFacebook(facebook);
				site.setLogo("/ecommerce/"+nameNormalize+"/logo."+extension);
				site = repository.save(site);
				
				ClientAdmin user = new ClientAdmin();
				user.setPassword("dabf1985");
				user.setPermission(1);
				user.setSite(site);
				user.setUser("daniel@danielfreire.net");
				crepository.save(user);
				
				generateFisicalSite(
						site,
						PortalTools.getInstance().getEcommerceProperties("location.patternsite"), 
						PortalTools.getInstance().getEcommerceProperties("location.generatesite") + "/" + nameNormalize);
				
				final String photoFile = PortalTools.getInstance().getEcommerceProperties("location.generatesite")+"/temp/"+logo;
				BufferedImage img = ImageIO.read(new File(photoFile));
				BufferedImage bi = new BufferedImage(img.getWidth(),
			    img.getHeight(), BufferedImage.TYPE_INT_RGB);
				Graphics2D grph = (Graphics2D) bi.getGraphics();
				grph.drawImage(img, 0, 0, null);
				grph.dispose();
				ImageIO.write(bi, extension, new File(PortalTools.getInstance().getEcommerceProperties("location.generatesite") + "/" + nameNormalize + "/logo." + extension));
			}
		} else {
			resp = PortalTools.getInstance().getRespError(error);
		}
		
		return resp;
	}
	
	@Override
	public void upload(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		if (!isMultipart) {
			response.getWriter().print("$('#alertError').val('"+PortalTools.getInstance().getMessage("upload.invalid")+"');$('#alertError').click()");
		} else {
			
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<?> items = upload.parseRequest(request);
			Iterator<?> itr = items.iterator();
			
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				if (!item.isFormField()){
					String itemName = item.getName();
					
					int IndexOf = itemName.indexOf(".");
					String domainName = itemName.substring(IndexOf);
					
					if (!domainName.toLowerCase().equals(".jpg") && !domainName.toLowerCase().equals(".png") && !domainName.toLowerCase().equals(".jpeg") ) {
						response.getWriter().print("$('#alertError').val('"+PortalTools.getInstance().getMessage("file.invalid")+"');$('#alertError').click()");
					} else {
						String finalimage = (new Date().getTime())+domainName;
						
						String photoFile = PortalTools.getInstance().getEcommerceProperties("location.generatesite")+
								"/temp/"+finalimage;
						
						File savedFile = new File(photoFile);
						item.write(savedFile);
						
						String reponseText = "uploadSet('"+finalimage+"');";
						
						response.getWriter().print(reponseText);
					}
				}
			}
			
	  	}
		
	}
	
	
	private void generateFisicalSite(Site site, String dirFrom, String dirTo) throws Exception {
		File to = new File(dirTo);
		if (!to.exists()) {
			to.mkdir();
		} else {
			to.delete();
			to.mkdir();
		}
		
		PortalTools.getInstance().copyDirectory(new File(dirFrom), new File(dirTo));

		Map<String, String> mapHome = new HashMap<String, String>();
		mapHome.put("${title}", site.getName());
		if (!ValidateTools.getInstancia().isNullEmpty(site.getFacebook())) {
			mapHome.put("${facebook-like}", "<div class=\"fb-like\" data-href=\""+site.getFacebook()+"\" data-send=\"true\" data-layout=\"button_count\" data-show-faces=\"true\"></div><br><br>");
			mapHome.put("${facebook-conf}", "<div id=\"fb-root\"></div><script>(function(d, s, id) {var js, fjs = d.getElementsByTagName(s)[0];if (d.getElementById(id)) return;js = d.createElement(s); js.id = id;js.src = \"//connect.facebook.net/en_US/all.js#xfbml=1\"; fjs.parentNode.insertBefore(js, fjs);}(document, 'script', 'facebook-jssdk'));</script>");
		} else {
			mapHome.put("${facebook-like}", "");
			mapHome.put("${facebook-conf}", "");
		}
		ConvertTools.getInstance().replaceFile(mapHome, new File(dirTo+"/index.html"));
		
		Map<String, String> mapFunctions = new HashMap<String, String>();
		mapFunctions.put("${altBanner}", site.getName() + " " + site.getDescription());
		mapFunctions.put("${urlHome}", "/ecommerce/" + ConvertTools.getInstance().normalizeString(site.getName()));
		mapFunctions.put("${sid}", PortalTools.getInstance().Encode(site.getId().toString()));
		mapFunctions.put("${context}", ConvertTools.getInstance().normalizeString(site.getName()));
		mapFunctions.put("${logo}", site.getLogo());
		ConvertTools.getInstance().replaceFile(mapFunctions, new File(dirTo+"/js/functions.js"));
		
		Map<String, String> mapCategory = new HashMap<String, String>();
		mapCategory.put("${title}", site.getName() + " - Categoria");
		ConvertTools.getInstance().replaceFile(mapCategory, new File(dirTo+"/category/index.html"));
		
		Map<String, String> mapClient = new HashMap<String, String>();
		mapClient.put("${title}", site.getName() + " - Painel de controle");
		ConvertTools.getInstance().replaceFile(mapClient, new File(dirTo+"/client/index.html"));
		
		Map<String, String> mapMycart = new HashMap<String, String>();
		mapMycart.put("${title}", site.getName() + " - Meu carrinho");
		ConvertTools.getInstance().replaceFile(mapMycart, new File(dirTo+"/mycart/index.html"));
		
		Map<String, String> mapProduct = new HashMap<String, String>();
		mapProduct.put("${title}", site.getName() + " - Produto");
		ConvertTools.getInstance().replaceFile(mapProduct, new File(dirTo+"/product/index.html"));
	}

}
