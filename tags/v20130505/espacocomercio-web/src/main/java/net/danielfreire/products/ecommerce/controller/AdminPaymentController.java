package net.danielfreire.products.ecommerce.controller;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.ecommerce.model.core.PaymentBusiness;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;
import net.danielfreire.util.PortalTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminPaymentController {
	
	@Autowired
	private transient PaymentBusiness paymentBusiness;
	
	@RequestMapping(value="/admin/payment/insert", method = RequestMethod.POST)
	public @ResponseBody GenericResponse paymentinsert(final HttpServletRequest request) {
		GenericResponse resp;
		try {
			resp = paymentBusiness.insertUpdate(request);
		} catch (Exception e) {
			resp = PortalTools.getInstance().getRespError(e);
		}
		
		return resp;
	}
	
	@RequestMapping(value="/admin/payment/remove", method = RequestMethod.POST)
	public @ResponseBody GenericResponse paymentremove(final HttpServletRequest request) {
		GenericResponse resp;
		try {
			resp = paymentBusiness.remove(request);
		} catch (Exception e) {
			resp = PortalTools.getInstance().getRespError(e);
		}
		
		return resp;
	}
	
	@RequestMapping(value="/admin/payment/consult", method = RequestMethod.GET)
	public @ResponseBody GridResponse paymentconsult(final HttpServletRequest request) {
		GridResponse resp;
		try {
			resp = paymentBusiness.consult(request);
		} catch (Exception e) {
			PortalTools.getInstance().getRespError(e);
			resp = new GridResponse();
		}
		
		return resp;
	}
	
}
