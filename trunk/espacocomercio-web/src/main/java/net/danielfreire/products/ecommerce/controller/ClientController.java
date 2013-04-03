package net.danielfreire.products.ecommerce.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.danielfreire.products.ecommerce.model.core.ClientEcommerceBusiness;
import net.danielfreire.products.ecommerce.model.core.ClientForAdminBusiness;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;
import net.danielfreire.util.PortalTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ClientController {
	
	@Autowired
	ClientEcommerceBusiness business;
	@Autowired
	ClientForAdminBusiness clientAdmBusiness;
	
	@RequestMapping(value="/admin/client/consult", method = RequestMethod.GET)
	public @ResponseBody GridResponse categoryconsult(HttpServletRequest request) {
		try {
			return clientAdmBusiness.consult(request);
		} catch (Exception e) {
			PortalTools.getInstance().getRespError(e);
			return new GridResponse();
		}
	}
	
	@RequestMapping(value="/admin/client/update", method = RequestMethod.POST)
	public @ResponseBody GenericResponse clientupdate(HttpServletRequest request) {
		try {
			return clientAdmBusiness.updateAdmin(request);
		} catch (Exception e) {
			PortalTools.getInstance().getRespError(e);
			return new GenericResponse();
		}
	}
	
	@RequestMapping(value="/admin/client/list", method = RequestMethod.GET)
	public @ResponseBody GenericResponse list(HttpServletRequest request) {
		try {
			return clientAdmBusiness.list(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/active", method = RequestMethod.GET)
	public void active(HttpServletRequest request, HttpServletResponse response) {
		try {
			business.active(request, response);
		} catch (Exception e) {
			PortalTools.getInstance().getRespError(e);
			try {
				response.getWriter().print("<html><head><title>Sistema indisponivel</title></head><body><script>alert('Sistema indisponível no momento, tente em alguns minutos.');</script></body></html>");
			} catch (IOException e1) {
				PortalTools.getInstance().getRespError(e);
			}
		}
	}

}
