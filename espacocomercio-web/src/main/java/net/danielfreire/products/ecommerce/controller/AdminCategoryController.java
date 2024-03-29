package net.danielfreire.products.ecommerce.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.danielfreire.products.ecommerce.model.core.ProductCategoryBusiness;
import net.danielfreire.products.ecommerce.util.EcommerceUtil;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;
import net.danielfreire.util.PortalTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminCategoryController {
	
	@Autowired
	private transient ProductCategoryBusiness categoryBusiness;

	@RequestMapping(value="/admin/category", method = RequestMethod.GET)
	public @ResponseBody GenericResponse category(final HttpServletRequest request, final HttpServletResponse response) {
		GenericResponse resp = new GenericResponse();
		try {
			resp.setGeneric(new Object[] {
					categoryBusiness.countCategorys(request), //0
					EcommerceUtil.getInstance().getSessionAdmin(request).getSite().getName()}); //1
		} catch (Exception e) {
			resp =  PortalTools.getInstance().getRespError(e);
		}
		
		return resp;
	}
	
	@RequestMapping(value="/admin/product/category/insert", method = RequestMethod.POST)
	public @ResponseBody GenericResponse categoryinsert(final HttpServletRequest request) {
		GenericResponse resp;
		try {
			resp = categoryBusiness.insert(request);
		} catch (Exception e) {
			resp = PortalTools.getInstance().getRespError(e);
		}
		
		return resp;
	}
	
	@RequestMapping(value="/admin/product/category/consult", method = RequestMethod.GET)
	public @ResponseBody GridResponse categoryconsult(final HttpServletRequest request) {
		GridResponse resp;
		try {
			resp = categoryBusiness.consult(request);
		} catch (Exception e) {
			PortalTools.getInstance().getRespError(e);
			resp = new GridResponse();
		}
		
		return resp;
	}
	
	@RequestMapping(value="/admin/product/category/list", method = RequestMethod.GET)
	public @ResponseBody GenericResponse categorylist(final HttpServletRequest request) {
		GenericResponse resp;
		try {
			resp = categoryBusiness.list(request);
		} catch (Exception e) {
			resp = PortalTools.getInstance().getRespError(e);
		}
		
		return resp;
	}
	
	@RequestMapping(value="/admin/product/category/update", method = RequestMethod.POST)
	public @ResponseBody GenericResponse categoryupdate(final HttpServletRequest request) {
		GenericResponse resp;
		try {
			resp = categoryBusiness.update(request);
		} catch (Exception e) {
			resp = PortalTools.getInstance().getRespError(e);
		}
		
		return resp;
	}
	
	@RequestMapping(value="/admin/product/category/remove", method = RequestMethod.POST)
	public @ResponseBody GenericResponse categoryremove(final HttpServletRequest request) {
		GenericResponse resp;
		try {
			resp = categoryBusiness.remove(request);
		} catch (Exception e) {
			resp = PortalTools.getInstance().getRespError(e);
		}
		
		return resp;
	}

}
