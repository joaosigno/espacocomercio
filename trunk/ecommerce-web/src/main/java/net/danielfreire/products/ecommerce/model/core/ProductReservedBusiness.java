package net.danielfreire.products.ecommerce.model.core;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.util.GenericResponse;

public interface ProductReservedBusiness {
	
	GenericResponse insert(HttpServletRequest request);
	
	GenericResponse update(HttpServletRequest request);
	
	GenericResponse listReserved(HttpServletRequest request);
	
	GenericResponse productIsReserved(HttpServletRequest request);

}
