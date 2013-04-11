package net.danielfreire.products.ecommerce.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.danielfreire.products.ecommerce.model.core.ProductAdminBusiness;
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
public class AdminProductController {

	@Autowired
	private transient ProductAdminBusiness productBusiness;

	@RequestMapping(value="/admin/product", method = RequestMethod.GET)
	public @ResponseBody GenericResponse home(final HttpServletRequest request, final HttpServletResponse response) {
		GenericResponse resp = new GenericResponse();
		try {
			resp.setGeneric(new Object[] {
					EcommerceUtil.getInstance().getSessionAdmin(request).getSite().getName(), //0
					productBusiness.countProducts(request), //1
					productBusiness.countProductsSemEstoque(request), //2
					productBusiness.listProductsSemEstoque(request),//3
					productBusiness.listProductsOrderByEstoque(request),//4
					productBusiness.countQuantityAllProducts(request)}); //5
		} catch (Exception e) {
			resp =  PortalTools.getInstance().getRespError(e);
		}
		
		return resp;
	}
	
	@RequestMapping(value="/admin/product/insert", method = RequestMethod.POST)
	public @ResponseBody GenericResponse insert(final HttpServletRequest request) {
		GenericResponse resp;
		try {
			resp = productBusiness.insertUpdate(request);
		} catch (Exception e) {
			resp = PortalTools.getInstance().getRespError(e);
		}
		
		return resp;
	}
	
	@RequestMapping(value="/admin/product/consult", method = RequestMethod.GET)
	public @ResponseBody GridResponse consult(final HttpServletRequest request) {
		GridResponse resp;
		try {
			resp = productBusiness.consult(request);
		} catch (Exception e) {
			PortalTools.getInstance().getRespError(e);
			resp = new GridResponse();
		}
		
		return resp;
	}
	
	@RequestMapping(value="/admin/product/remove", method = RequestMethod.POST)
	public @ResponseBody GenericResponse remove(final HttpServletRequest request) {
		GenericResponse resp;
		try {
			resp = productBusiness.detele(request);
		} catch (Exception e) {
			resp = PortalTools.getInstance().getRespError(e);
		}
		
		return resp;
	}
	
	@RequestMapping(value="/admin/product/upload", method = RequestMethod.POST)
	public void productupload(final HttpServletRequest request, final HttpServletResponse response) {
		try {
			productBusiness.upload(request, response);
		} catch (Exception e) {
			try {
				response.getWriter().print("$('#alertError', top.document).val('"+PortalTools.getInstance().getRespError(e).getMessageError().toString()+"');$('#alertError', top.document).click();");
			} catch (IOException e1) {
				PortalTools.getInstance().getRespError(e1);
			}
		}
	}
}
