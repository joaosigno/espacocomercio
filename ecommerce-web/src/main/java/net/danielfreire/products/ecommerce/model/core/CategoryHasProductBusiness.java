package net.danielfreire.products.ecommerce.model.core;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.util.GenericResponse;

public interface CategoryHasProductBusiness {
	
	GenericResponse insert(HttpServletRequest request);
	
	GenericResponse update(HttpServletRequest request);
	
	GenericResponse listProductByCategory(HttpServletRequest request);

}
