package net.danielfreire.products.ecommerce.model.core;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;

public interface ProductBusiness {
	
	GenericResponse load(HttpServletRequest request) throws java.lang.Exception;
	
	GenericResponse load(String siteId, String productId) throws java.lang.Exception;
	
	GridResponse listToPortal(String sid, String categoryId, String search, String page) throws java.lang.Exception;
	
	GenericResponse addCart(HttpServletRequest request) throws java.lang.Exception;
	
	GenericResponse myCart(HttpServletRequest request) throws java.lang.Exception;
	
	GenericResponse removeItemCart(HttpServletRequest request) throws java.lang.Exception;

}
