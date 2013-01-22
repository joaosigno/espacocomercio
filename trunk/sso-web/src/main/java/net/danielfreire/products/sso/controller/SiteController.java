package net.danielfreire.products.sso.controller;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.sso.model.core.SiteBusiness;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SiteController {
	
	@Autowired
	private SiteBusiness siteBusiness;
	
	@RequestMapping(value="/site/updatePortal", method = RequestMethod.POST)
	public @ResponseBody GenericResponse update(String nome, String desc, HttpServletRequest request) {
		return siteBusiness.update(nome, desc, request);
	}
	
	@RequestMapping(value="/site/insert", method = RequestMethod.POST)
	public @ResponseBody GenericResponse insert(HttpServletRequest request) {
		return siteBusiness.insert(request);
	}
	
	@RequestMapping(value="/site/consult", method = RequestMethod.GET)
	public @ResponseBody GridResponse consult(HttpServletRequest request) {
		return siteBusiness.consult(request);
	}

	@RequestMapping(value="/site/update", method = RequestMethod.POST)
	public @ResponseBody GenericResponse update(HttpServletRequest request) {
		return siteBusiness.update(request);
	}
	
	@RequestMapping(value="/site/remove", method = RequestMethod.POST)
	public @ResponseBody GenericResponse remove(HttpServletRequest request) {
		return siteBusiness.remove(request);
	}
	
}
