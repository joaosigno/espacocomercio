package net.danielfreire.products.ecommerce.model.core;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.ecommerce.model.domain.ClientAdmin;
import net.danielfreire.products.ecommerce.model.domain.Site;
import net.danielfreire.products.ecommerce.model.repository.ClientAdminRepository;
import net.danielfreire.products.ecommerce.model.repository.SiteRepository;
import net.danielfreire.products.ecommerce.util.EcommerceUtil;
import net.danielfreire.util.ConvertTools;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.PortalTools;
import net.danielfreire.util.ValidateTools;

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
		
		HashMap<String, String> error = new HashMap<String, String>();
		if (ValidateTools.getInstancia().isNullEmpty(name)) {
			error.put("name", PortalTools.getInstance().getMessage("name.invalid"));
		}
		if (!ValidateTools.getInstancia().isUrl(facebook)) {
			error.put("facebook", PortalTools.getInstance().getMessage("facebook.invalid"));
		}
		
		if (error.size()==0) {
			if (ValidateTools.getInstancia().isNullEmpty(id)) {
				Site site = new Site();
				site.setDescription(description);
				site.setName(name);
				site.setFacebook(facebook);
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
						PortalTools.getInstance().getEcommerceProperties("location.generatesite") + "/" + ConvertTools.getInstance().normalizeString(name));
			}
		} else {
			resp = PortalTools.getInstance().getRespError(error);
		}
		
		return resp;
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
			mapHome.put("${facebook-like}", "<div class=\"fb-like\" data-href=\""+site.getFacebook()+"\" data-send=\"true\" data-layout=\"button_count\" data-show-faces=\"true\"></div>");
			mapHome.put("${facebook-conf}", "<div id=\"fb-root\"></div><script>(function(d, s, id) {var js, fjs = d.getElementsByTagName(s)[0];if (d.getElementById(id)) return;js = d.createElement(s); js.id = id;js.src = \"//connect.facebook.net/en_US/all.js#xfbml=1\"; fjs.parentNode.insertBefore(js, fjs);}(document, 'script', 'facebook-jssdk'));</script>");
		}
		ConvertTools.getInstance().replaceFile(mapHome, new File(dirTo+"/index.html"));
		
		Map<String, String> mapFunctions = new HashMap<String, String>();
		mapFunctions.put("${altBanner}", site.getName() + " " + site.getDescription());
		mapFunctions.put("${urlHome}", "/ecommerce/" + ConvertTools.getInstance().normalizeString(site.getName()));
		mapFunctions.put("${sid}", PortalTools.getInstance().Encode(site.getId().toString()));
		mapFunctions.put("${context}", ConvertTools.getInstance().normalizeString(site.getName()));
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
