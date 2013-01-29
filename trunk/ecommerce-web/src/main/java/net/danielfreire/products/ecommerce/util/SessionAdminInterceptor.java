package net.danielfreire.products.ecommerce.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.danielfreire.products.ecommerce.model.domain.ClientAdmin;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.PortalTools;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.gson.Gson;

public class SessionAdminInterceptor extends HandlerInterceptorAdapter {
	
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
			final String uri = request.getRequestURI();
			
			if (!uri.contains("/admin/login") && !uri.contains("/admin/logout")) {
				ClientAdmin client = EcommerceUtil.getInstance().getSessionAdmin(request);
				if (client!=null) {
					return new GenericResponse();
				} else {
					return PortalTools.getInstance().getRespError("session.invalid");
				}
			} else {
				return new GenericResponse();
			}
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError("session.invalid");
		}
	}

}
