package net.danielfreire.products.advocacy.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.danielfreire.products.advocacy.model.domain.AdvocacyUser;
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
			AdvocacyUser user = AdvocacyUtil.getInstancia().getSessionUser(request);
			if (user != null) {
				GenericResponse resp = new GenericResponse();
				
				final String uri = request.getRequestURI();
				
				boolean permissionValid = true;
				if (!user.getManageUser() && (uri.contains("/advocacy-web/admin/office/user/") || uri.contains("/admin/office/usersession/"))) {
					permissionValid = false;
				} else if (!user.getManageFinance() && uri.contains("/advocacy-web/admin/finance/")) {
					permissionValid = false;
				} else if (!user.getManageClient() && uri.contains("/advocacy-web/admin/client/")) {
					permissionValid = false;
				} else if (!user.getManageLawyer() && uri.contains("/advocacy-web/admin/lawyer/")) {
					permissionValid = false;
				}
				
				if (!permissionValid) {
					resp = PortalTools.getInstance().getRespError("permission.invalid");
				}
				
				return resp;
			} else {
				return PortalTools.getInstance().getRespError("session.invalid");
			}
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError("session.invalid");
		}
	}
	
}
