package net.danielfreire.products.ecommerce.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.danielfreire.products.ecommerce.model.core.ProductBusiness;
import net.danielfreire.products.ecommerce.model.core.ProductCategoryBusiness;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;
import net.danielfreire.util.PortalTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductController {
	
	@Autowired
	ProductCategoryBusiness categoryBusiness;
	@Autowired
	ProductBusiness productBusiness;
	
	@RequestMapping(value="/admin/product/category/insert", method = RequestMethod.POST)
	public @ResponseBody GenericResponse categoryinsert(HttpServletRequest request) {
		try {
			return categoryBusiness.insert(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/admin/product/category/consult", method = RequestMethod.GET)
	public @ResponseBody GridResponse categoryconsult(HttpServletRequest request) {
		try {
			return categoryBusiness.consult(request);
		} catch (Exception e) {
			PortalTools.getInstance().getRespError(e);
			return new GridResponse();
		}
	}
	
	@RequestMapping(value="/admin/product/category/list", method = RequestMethod.GET)
	public @ResponseBody GenericResponse categorylist(HttpServletRequest request) {
		try {
			return categoryBusiness.list(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/admin/product/category/update", method = RequestMethod.POST)
	public @ResponseBody GenericResponse categoryupdate(HttpServletRequest request) {
		try {
			return categoryBusiness.update(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/admin/product/category/remove", method = RequestMethod.POST)
	public @ResponseBody GenericResponse categoryremove(HttpServletRequest request) {
		try {
			return categoryBusiness.remove(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/admin/product/insert", method = RequestMethod.POST)
	public @ResponseBody GenericResponse insert(HttpServletRequest request) {
		try {
			return productBusiness.insertUpdate(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/admin/product/consult", method = RequestMethod.GET)
	public @ResponseBody GridResponse consult(HttpServletRequest request) {
		try {
			return productBusiness.consult(request);
		} catch (Exception e) {
			PortalTools.getInstance().getRespError(e);
			return new GridResponse();
		}
	}
	
	@RequestMapping(value="/admin/product/load", method = RequestMethod.GET)
	public @ResponseBody GenericResponse load(HttpServletRequest request) {
		try {
			return productBusiness.load(request);
		} catch (Exception e) {
			PortalTools.getInstance().getRespError(e);
			return new GenericResponse();
		}
	}
	
	@RequestMapping(value="/admin/product/remove", method = RequestMethod.POST)
	public @ResponseBody GenericResponse remove(HttpServletRequest request) {
		try {
			return productBusiness.detele(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}
	
	@RequestMapping(value="/admin/product/upload", method = RequestMethod.POST)
	public void productupload(HttpServletRequest request, HttpServletResponse response) {
		try {
			productBusiness.upload(request, response);
		} catch (Exception e) {
			try {
				response.getWriter().print("<script src=\"/library/js/jquery-1.8.3.min.js\"></script><script>$('#alertError', top.document).val('"+PortalTools.getInstance().getRespError(e).getMessageError().toString()+"');$('#alertError', top.document).click()</script>");
			} catch (IOException e1) {
				PortalTools.getInstance().getRespError(e1);
			}
		}
	}

}
