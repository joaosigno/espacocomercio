package net.danielfreire.products.ecommerce.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.danielfreire.products.ecommerce.model.core.SiteBusiness;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.PortalTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SiteController {
	
	@Autowired
	SiteBusiness business;
	
	@RequestMapping(value="/admin/site/load", method = RequestMethod.GET)
	public @ResponseBody GenericResponse loadsite(HttpServletRequest request) {
		try {
			return business.load(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/admin/site/save", method = RequestMethod.POST)
	public @ResponseBody GenericResponse save(HttpServletRequest request) {
		try {
			return business.save(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/admin/site/upload", method = RequestMethod.POST)
	public void siteupload(HttpServletRequest request, HttpServletResponse response) {
		try {
			business.upload(request, response);
		} catch (Exception e) {
			try {
				response.getWriter().print("$('#alertError').val('"+PortalTools.getInstance().getRespError(e).getMessageError().toString()+"');$('#alertError').click();");
			} catch (IOException e1) {
				PortalTools.getInstance().getRespError(e1);
			}
		}
	}

}
