package net.danielfreire.products.advocacy.controller;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.advocacy.model.core.AdvocacyUserBusiness;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;
import net.danielfreire.util.PortalTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdvocacyUserController {

	@Autowired
	private AdvocacyUserBusiness business;
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public @ResponseBody GenericResponse login(HttpServletRequest request) {
		try {
			return business.login(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/admin/menu", method = RequestMethod.GET)
	public @ResponseBody GenericResponse menu(HttpServletRequest request) {
		try {
			return business.menuAdmin(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/admin/office/user/manage", method = RequestMethod.POST)
	public @ResponseBody GenericResponse manage(HttpServletRequest request) {
		try {
			return business.manage(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/admin/office/user/consult", method = RequestMethod.GET)
	public @ResponseBody GridResponse consult(HttpServletRequest request) {
		return business.consult(request);
	}
	
	@RequestMapping(value="/admin/office/user/load", method = RequestMethod.GET)
	public @ResponseBody GenericResponse load(HttpServletRequest request) {
		try {
			return business.load(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
}