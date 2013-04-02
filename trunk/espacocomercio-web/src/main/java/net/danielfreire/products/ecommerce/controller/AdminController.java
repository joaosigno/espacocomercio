package net.danielfreire.products.ecommerce.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.danielfreire.products.ecommerce.model.core.MessageBusiness;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.PortalTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController {
	
	@Autowired
	private transient MessageBusiness messageBusiness;

	@RequestMapping(value="/admin/message/count", method = RequestMethod.GET)
	public @ResponseBody GenericResponse countMessage(final HttpServletRequest request) {
		GenericResponse resp = new GenericResponse();
		try {
			resp = messageBusiness.countLastMessage(request);
		} catch (Exception e) {
			resp = PortalTools.getInstance().getRespError(e);
		}
		
		return resp;
	}
	
	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
	public @ResponseBody GenericResponse home(HttpServletRequest request, HttpServletResponse response) {
		try {
			return business.home(request, response);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
}
