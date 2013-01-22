package net.danielfreire.products.ecommerce.controller;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.ecommerce.model.core.ClientEcommerceBusiness;
import net.danielfreire.products.ecommerce.model.core.FreteParameterBusiness;
import net.danielfreire.products.ecommerce.model.core.OrderBusiness;
import net.danielfreire.products.ecommerce.model.core.ProductBusiness;
import net.danielfreire.products.ecommerce.model.core.ProductHasOrderBusiness;
import net.danielfreire.products.ecommerce.util.HeaderResponse;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;
import net.danielfreire.util.PortalTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PortalController {
	
	@Autowired
	private ProductBusiness productBusiness;
	@Autowired
	private ClientEcommerceBusiness clientEcommerceBusiness;
	@Autowired
	private OrderBusiness orderBusiness;
	@Autowired
	private ProductHasOrderBusiness productHasOrderBusiness;
	@Autowired
	private FreteParameterBusiness freteParameterBusiness;
	
	@RequestMapping(value="/header", method = RequestMethod.GET)
	public @ResponseBody HeaderResponse sitecategorylist(String sid) {
		try {
			
			HeaderResponse resp = new HeaderResponse();
			resp.setSessionCart(null);
			resp.setSessionClient(null);
			
			return resp;
		} catch (Exception e) {
			PortalTools.getInstance().getRespError(e);
			return new HeaderResponse();
		}
	}
	
	@RequestMapping(value="/products", method = RequestMethod.GET)
	public @ResponseBody GridResponse products(String sid, String cid, String search, String page) {
		try {
			return productBusiness.listToPortal(sid, cid, search, page);
		} catch (Exception e) {
			PortalTools.getInstance().getRespError(e);
			return new GridResponse();
		}
	}
	
	@RequestMapping(value="/product", method = RequestMethod.GET)
	public @ResponseBody GenericResponse products(String pid, String sid) {
		try {
			return productBusiness.load(sid, pid);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/session", method = RequestMethod.GET)
	public @ResponseBody GenericResponse session(String sid, HttpServletRequest request) {
		try {
			GenericResponse resp = new GenericResponse();
			resp.setGeneric(clientEcommerceBusiness.portalSession(request, sid));
			
			return resp;
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/client/new", method = RequestMethod.POST)
	public @ResponseBody GenericResponse clientNew(HttpServletRequest request) {
		try {
			return clientEcommerceBusiness.insert(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public @ResponseBody GenericResponse login(HttpServletRequest request) {
		try {
			return clientEcommerceBusiness.login(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/logoutSession", method = RequestMethod.POST)
	public @ResponseBody GenericResponse logout(HttpServletRequest request) {
		try {
			request.getSession().removeAttribute(PortalTools.getInstance().idSession);
			return new GenericResponse();
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/forgotPassword", method = RequestMethod.GET)
	public @ResponseBody GenericResponse forgotPassword(HttpServletRequest request) {
		try {
			return clientEcommerceBusiness.forgotPassword(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/orders", method = RequestMethod.GET)
	public @ResponseBody GenericResponse orders(HttpServletRequest request) {
		try {
			return orderBusiness.listByOpt(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/order/products", method = RequestMethod.GET)
	public @ResponseBody GenericResponse orderproducts(HttpServletRequest request) {
		try {
			return productHasOrderBusiness.list(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/updatePassword", method = RequestMethod.POST)
	public @ResponseBody GenericResponse updatePassword(HttpServletRequest request) {
		try {
			return clientEcommerceBusiness.updatePassword(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/updateAddress", method = RequestMethod.POST)
	public @ResponseBody GenericResponse updateAddress(HttpServletRequest request) {
		try {
			return clientEcommerceBusiness.updateAddress(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/updateData", method = RequestMethod.POST)
	public @ResponseBody GenericResponse updateData(HttpServletRequest request) {
		try {
			return clientEcommerceBusiness.updateData(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/addCart", method = RequestMethod.POST)
	public @ResponseBody GenericResponse addCart(HttpServletRequest request) {
		try {
			return productBusiness.addCart(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/order", method = RequestMethod.POST)
	public @ResponseBody GenericResponse order(String sid, HttpServletRequest request) {
		try {
			return orderBusiness.insert(request, clientEcommerceBusiness.portalSession(request, sid));
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/mycart", method = RequestMethod.GET)
	public @ResponseBody GenericResponse mycart(HttpServletRequest request) {
		try {
			return productBusiness.myCart(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/getFrete", method = RequestMethod.GET)
	public @ResponseBody GenericResponse getFrete(HttpServletRequest request) {
		try {
			return freteParameterBusiness.getFrete(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
}
