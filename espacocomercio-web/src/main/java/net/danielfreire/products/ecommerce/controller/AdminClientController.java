package net.danielfreire.products.ecommerce.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class AdminClientController {

	@Autowired
	private transient ClientForAdminBusiness clientBusiness;

	@RequestMapping(value="/admin/client", method = RequestMethod.GET)
	public @ResponseBody GenericResponse client(final HttpServletRequest request, final HttpServletResponse response) {
		GenericResponse resp = new GenericResponse();
		try {
			resp.setGeneric(clientBusiness.countClients(request));
		} catch (Exception e) {
			resp =  PortalTools.getInstance().getRespError(e);
		}
		
		return resp;
	}
	
	@RequestMapping(value="/admin/client/consult", method = RequestMethod.GET)
	public @ResponseBody GridResponse categoryconsult(final HttpServletRequest request) {
		GridResponse resp;
		try {
			resp = clientBusiness.consult(request);
		} catch (Exception e) {
			PortalTools.getInstance().getRespError(e);
			resp = new GridResponse();
		}
		
		return resp;
	}
	
	@RequestMapping(value="/admin/client/update", method = RequestMethod.POST)
	public @ResponseBody GenericResponse clientupdate(final HttpServletRequest request) {
		GenericResponse resp;
		try {
			resp = clientBusiness.updateAdmin(request);
		} catch (Exception e) {
			PortalTools.getInstance().getRespError(e);
			resp = new GenericResponse();
		}
		
		return resp;
	}
	
	@RequestMapping(value="/admin/client/list", method = RequestMethod.GET)
	public @ResponseBody GenericResponse list(final HttpServletRequest request) {
		GenericResponse resp;
		try {
			resp = clientBusiness.list(request);
		} catch (Exception e) {
			resp = PortalTools.getInstance().getRespError(e);
		}
		
		return resp;
	}
}
