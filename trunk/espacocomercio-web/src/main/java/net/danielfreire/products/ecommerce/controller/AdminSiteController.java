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
public class AdminSiteController {
	
	@Autowired
	private transient SiteBusiness business;
	
	@RequestMapping(value="/admin/site/load", method = RequestMethod.GET)
	public @ResponseBody GenericResponse loadsite(final HttpServletRequest request) {
		GenericResponse resp;
		try {
			resp = business.load(request);
		} catch (Exception e) {
			resp = PortalTools.getInstance().getRespError(e);
		}
		 return resp;
	}
	
	@RequestMapping(value="/admin/site/save", method = RequestMethod.POST)
	public @ResponseBody GenericResponse save(final HttpServletRequest request) {
		GenericResponse resp;
		try {
			resp = business.save(request);
		} catch (Exception e) {
			resp = PortalTools.getInstance().getRespError(e);
		}
		
		return resp;
	}
	
	@RequestMapping(value="/admin/site/update", method = RequestMethod.POST)
	public @ResponseBody GenericResponse update(final HttpServletRequest request) {
		GenericResponse resp;
		try {
			resp = business.save(request);
		} catch (Exception e) {
			resp = PortalTools.getInstance().getRespError(e);
		}
		
		return resp;
	}
	
	@RequestMapping(value="/admin/site/upload", method = RequestMethod.POST)
	public void siteupload(final HttpServletRequest request, final HttpServletResponse response) {
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
