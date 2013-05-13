package net.danielfreire.products.ecommerce.controller;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.ecommerce.model.core.ProductBusiness;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.PortalTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductController {
	
	@Autowired
	private transient ProductBusiness productBusiness;
	
	@RequestMapping(value="/admin/product/load", method = RequestMethod.GET)
	public @ResponseBody GenericResponse load(final HttpServletRequest request) {
		GenericResponse resp;
		try {
			resp = productBusiness.load(request);
		} catch (Exception e) {
			PortalTools.getInstance().getRespError(e);
			resp = new GenericResponse();
		}
		
		return resp;
	}

}
