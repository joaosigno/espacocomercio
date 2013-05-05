package net.danielfreire.products.ecommerce.controller;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.ecommerce.model.core.ClientEcommerceBusiness;
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
public class AdminOrderController {

	@Autowired
	private transient OrderBusiness orderBusiness;
	@Autowired
	private transient ProductHasOrderBusiness pdtHasOrder;
	@Autowired
	private transient ClientEcommerceBusiness clientBusiness;
	@Autowired
	private transient PaymentBusiness paymentBusiness;
	
	@RequestMapping(value="/admin/order", method = RequestMethod.GET)
	public @ResponseBody GenericResponse order(final HttpServletRequest request) {
		GenericResponse resp = new GenericResponse();
		try {
			final Long counts[] = {
					orderBusiness.countOrderByStatus(OrderBusiness.ID_STS_AGUARD_PG, request), 
					orderBusiness.countOrderByStatus(OrderBusiness.ID_STS_PG_CONFIRM, request), 
					orderBusiness.countOrderByStatus(OrderBusiness.ID_STS_ENVIADO, request) + orderBusiness.countOrderByStatus(OrderBusiness.ID_STS_FINALIZADO, request),
					orderBusiness.countOrderByStatus(OrderBusiness.ID_STS_CANCELADO, request)}; 
			resp.setGeneric(counts);
		} catch (Exception e) {
			resp = PortalTools.getInstance().getRespError(e);
		}
		
		return resp;
	}
	
	@RequestMapping(value="/admin/order/consult", method = RequestMethod.GET)
	public @ResponseBody GridResponse categoryconsult(final HttpServletRequest request) {
		GridResponse resp;
		try {
			resp = orderBusiness.consult(request);
		} catch (Exception e) {
			PortalTools.getInstance().getRespError(e);
			resp = new GridResponse();
		}
		
		return resp;
	}
	
	@RequestMapping(value="/admin/order/product/list", method = RequestMethod.GET)
	public @ResponseBody GenericResponse orderproductlist(final HttpServletRequest request) {
		GenericResponse resp;
		try {
			resp = pdtHasOrder.list(request);
		} catch (Exception e) {
			resp = PortalTools.getInstance().getRespError(e);
		}
		
		return resp;
	}
	
	@RequestMapping(value="/admin/order/update", method = RequestMethod.POST)
	public @ResponseBody GenericResponse orderupdate(final HttpServletRequest request) {
		GenericResponse resp;
		try {
			resp = orderBusiness.update(request);
		} catch (Exception e) {
			resp = PortalTools.getInstance().getRespError(e);
		}
		
		return resp;
	}
	
	@RequestMapping(value="/admin/order/client", method = RequestMethod.GET)
	public @ResponseBody GenericResponse orderclient(final Integer oid) {
		GenericResponse resp = new GenericResponse();
		try {
			resp.setGeneric( clientBusiness.getClient(oid) );
		} catch (Exception e) {
			resp = PortalTools.getInstance().getRespError(e);
		}
		
		return resp;
	}
	
	@RequestMapping(value="/admin/payment/list", method = RequestMethod.GET)
	public @ResponseBody GenericResponse paymentlist(final HttpServletRequest request) {
		GenericResponse resp;
		try {
			resp = paymentBusiness.list(request);
		} catch (Exception e) {
			resp = PortalTools.getInstance().getRespError(e);
		}
		
		return resp;
	}
}
