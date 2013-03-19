package net.danielfreire.products.ecommerce.controller;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.ecommerce.model.core.FreteParameterBusiness;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;
import net.danielfreire.util.PortalTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FreteController {
	
	@Autowired
	private FreteParameterBusiness freteParameterBusiness;
	
	@RequestMapping(value="/admin/frete/consult", method = RequestMethod.GET)
	public @ResponseBody GridResponse freteconsult(HttpServletRequest request) {
		try {
			return freteParameterBusiness.consult(request);
		} catch (Exception e) {
			PortalTools.getInstance().getRespError(e);
			return new GridResponse();
		}
	}
	
	@RequestMapping(value="/admin/frete/save", method = RequestMethod.POST)
	public @ResponseBody GenericResponse fretesave(HttpServletRequest request) {
		try {
			return freteParameterBusiness.insertUpdate(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
}
