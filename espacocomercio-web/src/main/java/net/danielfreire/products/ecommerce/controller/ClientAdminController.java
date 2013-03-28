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
	ClientAdminBusiness business;
	
	@RequestMapping(value="/admin/login", method = RequestMethod.POST)
	public @ResponseBody GenericResponse adminlogin(HttpServletRequest request) {
		try {
			return business.login(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/admin/logout", method = RequestMethod.GET)
	public @ResponseBody void logout(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			request.getSession().removeAttribute(PortalTools.getInstance().idAdminSession);
			
			response.setContentType("text/html");
		    PrintWriter out = response.getWriter();

		    out.println("<HTML><HEAD><script>location.href='/ecommerce';</script></HEAD><BODY></BODY></HTML>");
			
		} catch (Exception e) {
			PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/admin/menu", method = RequestMethod.GET)
	public @ResponseBody GenericResponse menu(HttpServletRequest request) {
		try {
			return business.menu(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/admin/user/consult", method = RequestMethod.GET)
	public @ResponseBody GridResponse categoryconsult(HttpServletRequest request) {
		try {
			return business.consult(request);
		} catch (Exception e) {
			PortalTools.getInstance().getRespError(e);
			return new GridResponse();
		}
	}
	
	@RequestMapping(value="/admin/user/save", method = RequestMethod.POST)
	public @ResponseBody GenericResponse save(HttpServletRequest request) {
		try {
			return business.save(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/admin/user/load", method = RequestMethod.GET)
	public @ResponseBody GenericResponse load(HttpServletRequest request) {
		try {
			return business.load(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/admin/user/remove", method = RequestMethod.POST)
	public @ResponseBody GenericResponse remove(HttpServletRequest request) {
		try {
			return business.remove(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
	public @ResponseBody GenericResponse home(HttpServletRequest request) {
		try {
			return business.home(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}

}
