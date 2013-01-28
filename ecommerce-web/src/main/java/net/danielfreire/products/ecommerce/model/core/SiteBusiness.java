package net.danielfreire.products.ecommerce.model.core;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.util.GenericResponse;

public interface SiteBusiness {

	GenericResponse load(HttpServletRequest request) throws Exception;
	
	GenericResponse save(HttpServletRequest request) throws Exception;

}
