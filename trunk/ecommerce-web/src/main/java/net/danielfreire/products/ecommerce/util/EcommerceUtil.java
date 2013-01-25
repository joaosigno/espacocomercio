package net.danielfreire.products.ecommerce.util;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.ecommerce.model.domain.ClientAdmin;
import net.danielfreire.util.PortalTools;


public class EcommerceUtil {

	private static EcommerceUtil util = new EcommerceUtil();
	
	public static synchronized EcommerceUtil getInstance() {
        return util;
    }
	
	public void sessionAdminUser(HttpServletRequest request, ClientAdmin clientAdmin) {
		clientAdmin.setPassword("***");
		request.getSession().setAttribute(PortalTools.getInstance().idAdminSession, clientAdmin);
	}
	
	public ClientAdmin getSessionAdmin(HttpServletRequest request) {
		return (ClientAdmin) request.getSession().getAttribute(PortalTools.getInstance().idAdminSession);
	}
}
