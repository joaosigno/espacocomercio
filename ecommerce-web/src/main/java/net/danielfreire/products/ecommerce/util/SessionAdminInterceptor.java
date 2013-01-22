package net.danielfreire.products.ecommerce.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.PortalTools;
import net.danielfreire.util.ValidateTools;

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
			if (ValidateTools.getInstancia().isNumber(request.getSession().getAttribute(PortalTools.getInstance().idAdminSession).toString())) {
				return new GenericResponse();
			} else {
				return PortalTools.getInstance().getRespError("session.invalid");
			}
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError("session.invalid");
		}
	}

}
