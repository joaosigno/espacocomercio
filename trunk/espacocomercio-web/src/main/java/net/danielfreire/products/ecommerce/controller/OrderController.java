package net.danielfreire.products.ecommerce.controller;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.ecommerce.model.core.OrderBusiness;
import net.danielfreire.products.ecommerce.model.core.PaymentBusiness;
import net.danielfreire.products.ecommerce.model.core.ProductHasOrderBusiness;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;
import net.danielfreire.util.PortalTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrderController {
	
	@Autowired
	private PaymentBusiness paymentBusiness;
	@Autowired
	private OrderBusiness orderBusiness;
	@Autowired
	private ProductHasOrderBusiness productHasOrderBusiness;
	
	@RequestMapping(value="/admin/order/consult", method = RequestMethod.GET)
	public @ResponseBody GridResponse categoryconsult(HttpServletRequest request) {
		try {
			return orderBusiness.consult(request);
		} catch (Exception e) {
			PortalTools.getInstance().getRespError(e);
			return new GridResponse();
		}
	}
	
	@RequestMapping(value="/admin/order/product/list", method = RequestMethod.GET)
	public @ResponseBody GenericResponse orderproductlist(HttpServletRequest request) {
		try {
			return productHasOrderBusiness.list(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/admin/order/update", method = RequestMethod.POST)
	public @ResponseBody GenericResponse orderupdate(HttpServletRequest request) {
		try {
			return orderBusiness.update(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/admin/payment/insert", method = RequestMethod.POST)
	public @ResponseBody GenericResponse paymentinsert(HttpServletRequest request) {
		try {
			return paymentBusiness.insertUpdate(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/admin/payment/remove", method = RequestMethod.POST)
	public @ResponseBody GenericResponse paymentremove(HttpServletRequest request) {
		try {
			return paymentBusiness.remove(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/admin/payment/consult", method = RequestMethod.GET)
	public @ResponseBody GridResponse paymentconsult(HttpServletRequest request) {
		try {
			return paymentBusiness.consult(request);
		} catch (Exception e) {
			PortalTools.getInstance().getRespError(e);
			return new GridResponse();
		}
	}
	
	@RequestMapping(value="/admin/payment/list", method = RequestMethod.GET)
	public @ResponseBody GenericResponse paymentlist(HttpServletRequest request) {
		try {
			return paymentBusiness.list(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
}
