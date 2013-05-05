package net.danielfreire.products.ecommerce.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.danielfreire.products.ecommerce.model.core.ClientAdminBusiness;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;
import net.danielfreire.util.PortalTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ClientAdminController {
	
	@Autowired
	private transient ClientAdminBusiness business;
	
	@RequestMapping(value="/admin/login", method = RequestMethod.POST)
	public @ResponseBody GenericResponse adminlogin(final HttpServletRequest request) {
		GenericResponse resp;
		try {
			resp = business.login(request);
		} catch (Exception e) {
			resp = PortalTools.getInstance().getRespError(e);
		}
		
		return resp;
	}
	
	@RequestMapping(value="/admin/logout", method = RequestMethod.GET)
	public @ResponseBody void logout(final HttpServletRequest request, final HttpServletResponse response) {
		try {
			
			request.getSession().removeAttribute(PortalTools.ID_ADMIN_SESSION);
			
			response.setContentType("text/html");
		    final PrintWriter out = response.getWriter();

		    out.println("<HTML><HEAD><script>location.href='/ecommerce';</script></HEAD><BODY></BODY></HTML>");
			
		} catch (Exception e) {
			PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/admin/menu", method = RequestMethod.GET)
	public @ResponseBody GenericResponse menu(final HttpServletRequest request) {
		GenericResponse resp;
		try {
			resp = business.menu(request);
		} catch (Exception e) {
			resp = PortalTools.getInstance().getRespError(e);
		}
		
		return resp;
	}
	
	@RequestMapping(value="/admin/user/consult", method = RequestMethod.GET)
	public @ResponseBody GridResponse categoryconsult(final HttpServletRequest request) {
		GridResponse resp;
		try {
			resp = business.consult(request);
		} catch (Exception e) {
			PortalTools.getInstance().getRespError(e);
			resp = new GridResponse();
		}
		
		return resp;
	}
	
	@RequestMapping(value="/admin/user/save", method = RequestMethod.POST)
	public @ResponseBody GenericResponse save(final HttpServletRequest request) {
		GenericResponse resp;
		try {
			resp = business.save(request);
		} catch (Exception e) {
			resp = PortalTools.getInstance().getRespError(e);
		}
		return resp;
	}
	
	@RequestMapping(value="/admin/user/load", method = RequestMethod.GET)
	public @ResponseBody GenericResponse load(final HttpServletRequest request) {
		GenericResponse resp;
		try {
			resp = business.load(request);
		} catch (Exception e) {
			resp = PortalTools.getInstance().getRespError(e);
		}
		return resp;
	}
	
	@RequestMapping(value="/admin/user/remove", method = RequestMethod.POST)
	public @ResponseBody GenericResponse remove(final HttpServletRequest request) {
		GenericResponse resp;
		try {
			resp = business.remove(request);
		} catch (Exception e) {
			resp = PortalTools.getInstance().getRespError(e);
		}
		 return resp;
	}
	
}
