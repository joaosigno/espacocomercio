package net.danielfreire.products.ecommerce.controller;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.ecommerce.model.core.ClientAdminBusiness;
import net.danielfreire.util.GenericResponse;
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
	
	@RequestMapping(value="/admin/menu", method = RequestMethod.GET)
	public @ResponseBody GenericResponse menu(HttpServletRequest request) {
		try {
			return business.menu(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}

}
