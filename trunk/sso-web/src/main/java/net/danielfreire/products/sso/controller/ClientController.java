package net.danielfreire.products.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.danielfreire.products.sso.model.core.ClientBusiness;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;
import net.danielfreire.util.PortalTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ClientController {
	
	@Autowired
	private ClientBusiness clientBusiness;
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public @ResponseBody GenericResponse login(String user, String password, HttpServletRequest request) {
		return clientBusiness.login(user, password, request);
	}
	
	@RequestMapping(value="/client/insert", method = RequestMethod.POST)
	public @ResponseBody GenericResponse clientinsert(HttpServletRequest request) {
		return clientBusiness.insert(request);
	}
	
	@RequestMapping(value="/client/update", method = RequestMethod.POST)
	public @ResponseBody GenericResponse clientupdate(HttpServletRequest request) {
		return clientBusiness.update(request);
	}
	
	@RequestMapping(value="/client/remove", method = RequestMethod.POST)
	public @ResponseBody GenericResponse clientremove(HttpServletRequest request) {
		return clientBusiness.remove(request);
	}
	
	@RequestMapping(value="/client/load", method = RequestMethod.GET)
	public @ResponseBody GenericResponse clientload(HttpServletRequest request) {
		return clientBusiness.load(request);
	}
	
	@RequestMapping(value="/client/list", method = RequestMethod.GET)
	public @ResponseBody GenericResponse clientlist(HttpServletRequest request) {
		return clientBusiness.list(request);
	}
	
	@RequestMapping(value="/client/consult", method = RequestMethod.GET)
	public @ResponseBody GridResponse consult(HttpServletRequest request) {
		return clientBusiness.consult(request);
	}
	
	@RequestMapping(value="/client/admin/update", method = RequestMethod.POST)
	public @ResponseBody GenericResponse update(HttpServletRequest request) {
		try {
			return clientBusiness.updateAdmin(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		try {
			clientBusiness.logout(request, response);
		} catch (Exception e) {
			PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/session", method = RequestMethod.POST)
	public @ResponseBody GenericResponse session(HttpServletRequest request) {
		try {
			return clientBusiness.session(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}

}
