package net.danielfreire.products.ecommerce.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.danielfreire.products.ecommerce.model.core.ClientForAdminBusiness;
import net.danielfreire.products.ecommerce.model.core.MessageBusiness;
import net.danielfreire.products.ecommerce.model.core.OrderBusiness;
import net.danielfreire.products.ecommerce.model.core.SiteBusiness;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.PortalTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminHomeController {
	
	@Autowired
	private transient MessageBusiness messageBusiness;
	@Autowired
	private transient ClientForAdminBusiness clientBusiness;
	@Autowired
	private transient OrderBusiness orderBusiness;
	@Autowired
	private transient SiteBusiness siteBusiness;

	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
	public @ResponseBody GenericResponse home(final HttpServletRequest request, final HttpServletResponse response) {
		GenericResponse resp = new GenericResponse();
		try {
			resp.setGeneric(new Object[] {
					clientBusiness.countClients(request), //0
					orderBusiness.countOrders(request), //1
					siteBusiness.getNormalizeNameSiteBySessionAdmin(request), //2
					clientBusiness.getListClientByLastMonths(request),//3 
					orderBusiness.getListOrdersByLastMonths(request), //4
					clientBusiness.countLastClientsRegistry(request), //5
					orderBusiness.countLastOrders(request), //6
					orderBusiness.countLastOrdersPayment(request), //7
					messageBusiness.countLastMessage(request), //8
					orderBusiness.listLastOrders(request)}); //9
		} catch (Exception e) {
			resp =  PortalTools.getInstance().getRespError(e);
		}
		
		return resp;
	}
	
	@RequestMapping(value="/admin/msg", method = RequestMethod.GET)
	public @ResponseBody GenericResponse messages(final HttpServletRequest request, final HttpServletResponse response) {
		GenericResponse resp = new GenericResponse();
		try {
			resp.setGeneric(messageBusiness.countLastMessage(request));
		} catch (Exception e) {
			resp =  PortalTools.getInstance().getRespError(e);
		}
		
		return resp;
	}
}
