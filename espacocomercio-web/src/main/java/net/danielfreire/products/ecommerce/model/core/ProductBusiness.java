package net.danielfreire.products.ecommerce.model.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;

public interface ProductBusiness {
	
	GenericResponse insertUpdate(HttpServletRequest request) throws Exception;
	
	GenericResponse load(HttpServletRequest request) throws Exception;
	
	GenericResponse load(String siteId, String productId) throws Exception;
	
	GenericResponse detele(HttpServletRequest request) throws Exception;
	
	GenericResponse listByCategory(HttpServletRequest request) throws Exception;
	
	GridResponse consult(HttpServletRequest request) throws Exception;
	
	GridResponse listToPortal(String sid, String categoryId, String search, String page) throws Exception;
	
	void upload(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	GenericResponse addCart(HttpServletRequest request) throws Exception;
	
	GenericResponse myCart(HttpServletRequest request) throws Exception;
	
	GenericResponse removeItemCart(HttpServletRequest request) throws Exception;
	
}
