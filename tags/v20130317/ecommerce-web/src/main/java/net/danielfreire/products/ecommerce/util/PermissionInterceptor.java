package net.danielfreire.products.ecommerce.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.danielfreire.products.ecommerce.model.domain.ClientAdmin;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.PortalTools;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.gson.Gson;

public class PermissionInterceptor extends HandlerInterceptorAdapter {
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		GenericResponse resp = preHandle(request);
		if (resp.getStatus()) {
			return true;
		} else {
			response.getWriter().print(new Gson().toJson(resp));
			return false;
		}
	}

	private GenericResponse preHandle(HttpServletRequest request) {
		try {
			GenericResponse resp = new GenericResponse();
			ClientAdmin user = EcommerceUtil.getInstance().getSessionAdmin(request);
			
			if (user != null) {
				final String uri = request.getRequestURI();
				
				boolean permissionValid = true;
				if (user.getPermission()==3 && (uri.contains("/ecommerce-web/admin/site") || uri.contains("/ecommerce-web/admin/user") || uri.contains("/ecommerce-web/admin/finance"))) {
					permissionValid = false;
				} else if (user.getPermission()==4 && (uri.contains("/ecommerce-web/admin/site") || uri.contains("/ecommerce-web/admin/user") || uri.contains("/ecommerce-web/admin/finance") || uri.contains("/ecommerce-web/admin/client") || uri.contains("/ecommerce-web/admin/order") || uri.contains("/ecommerce-web/admin/frete")  || uri.contains("/ecommerce-web/admin/payment"))) {
					permissionValid = false;
				}
				
				if (!permissionValid) {
					resp = PortalTools.getInstance().getRespError("permission.invalid");
				}
			}
			
			return resp;
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
}
