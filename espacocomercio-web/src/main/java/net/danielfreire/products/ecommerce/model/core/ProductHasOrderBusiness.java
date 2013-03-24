package net.danielfreire.products.ecommerce.model.core;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.util.GenericResponse;

public interface ProductHasOrderBusiness {
	
	GenericResponse insert(HttpServletRequest request) throws Exception;
	
	GenericResponse update(HttpServletRequest request) throws Exception;
	
	GenericResponse list(HttpServletRequest request) throws Exception;

}
