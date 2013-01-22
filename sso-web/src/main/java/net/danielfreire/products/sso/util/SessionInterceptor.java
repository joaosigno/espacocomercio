package net.danielfreire.products.sso.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.danielfreire.products.sso.model.domain.Client;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.PortalTools;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.gson.Gson;

public class SessionInterceptor extends HandlerInterceptorAdapter {
	
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
			Client client = (Client) request.getSession().getAttribute(PortalTools.getInstance().idSession);
			if (client != null) {
				return new GenericResponse();
			} else {
				return PortalTools.getInstance().getRespError("session.invalid");
			}
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError("session.invalid");
		}
	}
}
