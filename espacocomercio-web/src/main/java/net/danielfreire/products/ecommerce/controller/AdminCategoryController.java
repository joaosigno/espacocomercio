package net.danielfreire.products.ecommerce.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.danielfreire.products.ecommerce.model.core.ProductCategoryBusiness;
import net.danielfreire.products.ecommerce.util.EcommerceUtil;
import net.danielfreire.util.GenericResponse;
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

}
