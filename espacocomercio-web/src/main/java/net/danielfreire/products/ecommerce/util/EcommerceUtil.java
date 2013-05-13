package net.danielfreire.products.ecommerce.util;

import java.util.ArrayList;
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
	
	public static EcommerceUtil getInstance() {
        return util;
    }
	
	public void sessionAdminUser(final HttpServletRequest request, final ClientAdmin clientAdmin) {
		clientAdmin.setPassword("***");
		request.getSession().removeAttribute(PortalTools.ID_ADMIN_SESSION);
		request.getSession().setAttribute(PortalTools.ID_ADMIN_SESSION, clientAdmin);
	}
	
	public ClientAdmin getSessionAdmin(final HttpServletRequest request) {
		return (ClientAdmin) request.getSession().getAttribute(PortalTools.ID_ADMIN_SESSION);
	}
	
	public void generateMenuPortal(final Site site, final List<ProductCategory> list) throws java.lang.Exception {
		final List<ProductCategory> listForMenu = new ArrayList<ProductCategory>();
		if (!list.isEmpty()) {
			for (ProductCategory category : list) {
				category.setSite(null);
				listForMenu.add(category);
			}
		}
		FileUtil.getInstance().createFile(
				PortalTools.getInstance().getEcommerceProperties("location.generatesite")+"/"+ConvertTools.getInstance().normalizeString(site.getName())+"/data/", 
				"menu.json", 
				new Gson().toJson(listForMenu));
	}
	
	public void generateProductCache(final Product product, final Site site) throws java.lang.Exception {
		FileUtil.getInstance().createFile(
				PortalTools.getInstance().getEcommerceProperties("location.generatesite")+"/"+ConvertTools.getInstance().normalizeString(site.getName())+"/data/",
				"product"+product.getKeyUrl()+".json", 
				new Gson().toJson(product));
	}
}
