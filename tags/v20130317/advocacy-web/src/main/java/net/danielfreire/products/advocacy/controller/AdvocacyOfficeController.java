package net.danielfreire.products.advocacy.controller;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.advocacy.model.core.AdvocacyOfficeBusiness;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;
import net.danielfreire.util.PortalTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdvocacyOfficeController {

	@Autowired
	private AdvocacyOfficeBusiness business;
	
	@RequestMapping(value="/admin/office/manage", method = RequestMethod.POST)
	public @ResponseBody GenericResponse login(HttpServletRequest request) {
		try {
			return business.manage(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/admin/office/consult", method = RequestMethod.GET)
	public @ResponseBody GridResponse consult(HttpServletRequest request) {
		return business.consult(request);
	}
	
	@RequestMapping(value="/admin/office/usersession/list", method = RequestMethod.GET)
	public @ResponseBody GenericResponse list(HttpServletRequest request) {
		try {
			return business.listBySession(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
}
