package net.danielfreire.products.advocacy.controller;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.advocacy.model.core.AdvocacyClientBusiness;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;
import net.danielfreire.util.PortalTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdvocayClientController {
	
	@Autowired
	private AdvocacyClientBusiness business;
	
	@RequestMapping(value="/admin/client/save", method = RequestMethod.POST)
	public @ResponseBody GenericResponse save(HttpServletRequest request) {
		try {
			return business.save(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/admin/client/consult", method = RequestMethod.GET)
	public @ResponseBody GridResponse consult(HttpServletRequest request) {
		try {
			return business.consult(request);
		} catch (Exception e) {
			PortalTools.getInstance().getRespError(e);
			return new GridResponse();
		}
	}
	
	@RequestMapping(value="/admin/client/load", method = RequestMethod.GET)
	public @ResponseBody GenericResponse load(HttpServletRequest request) {
		try {
			return business.load(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/admin/client/remove", method = RequestMethod.POST)
	public @ResponseBody GenericResponse remove(HttpServletRequest request) {
		try {
			return business.remove(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}

}
