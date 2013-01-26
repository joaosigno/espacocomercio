package net.danielfreire.products.advocacy.controller;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.advocacy.model.core.FinanceCategoryBusiness;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;
import net.danielfreire.util.PortalTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FinanceCategoryController {

	@Autowired
	private FinanceCategoryBusiness business;
	
	@RequestMapping(value="/admin/finance/category/consult", method = RequestMethod.GET)
	public @ResponseBody GridResponse consult(HttpServletRequest request) {
		return business.consult(request);
	}
	
	@RequestMapping(value="/admin/finance/category/manage", method = RequestMethod.POST)
	public @ResponseBody GenericResponse manage(HttpServletRequest request) {
		try {
			return business.manage(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/admin/finance/category/remove", method = RequestMethod.POST)
	public @ResponseBody GenericResponse remove(HttpServletRequest request) {
		try {
			return business.remove(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/admin/finance/category/list", method = RequestMethod.GET)
	public @ResponseBody GenericResponse list(HttpServletRequest request) {
		try {
			return business.list(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
}