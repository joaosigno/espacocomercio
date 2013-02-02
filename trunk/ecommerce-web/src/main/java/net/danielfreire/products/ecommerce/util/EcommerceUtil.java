package net.danielfreire.products.ecommerce.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.ecommerce.model.domain.ClientAdmin;
import net.danielfreire.products.ecommerce.model.domain.Product;
import net.danielfreire.products.ecommerce.model.domain.ProductCategory;
import net.danielfreire.products.ecommerce.model.domain.Site;
import net.danielfreire.util.ConvertTools;
import net.danielfreire.util.FileUtil;
import net.danielfreire.util.PortalTools;

import com.google.gson.Gson;


public class EcommerceUtil {

	private static EcommerceUtil util = new EcommerceUtil();
	
	public static synchronized EcommerceUtil getInstance() {
        return util;
    }
	
	public void sessionAdminUser(HttpServletRequest request, ClientAdmin clientAdmin) {
		clientAdmin.setPassword("***");
		request.getSession().removeAttribute(PortalTools.getInstance().idAdminSession);
		request.getSession().setAttribute(PortalTools.getInstance().idAdminSession, clientAdmin);
	}
	
	public ClientAdmin getSessionAdmin(HttpServletRequest request) {
		return (ClientAdmin) request.getSession().getAttribute(PortalTools.getInstance().idAdminSession);
	}
	
	public void generateMenuPortal(Site site, List<ProductCategory> list) throws Exception {
		FileUtil.getInstance().createFile(
				PortalTools.getInstance().getEcommerceProperties("location.generatesite")+"/"+ConvertTools.getInstance().normalizeString(site.getName())+"/data/", 
				"menu.json", 
				new Gson().toJson(list));
	}
	
	public void generateProductCache(Product p, Site site) throws Exception {
		FileUtil.getInstance().createFile(
				PortalTools.getInstance().getEcommerceProperties("location.generatesite")+"/"+ConvertTools.getInstance().normalizeString(site.getName())+"/data/",
				"product"+p.getKeyUrl()+".json", 
				new Gson().toJson(p));
	}
}
