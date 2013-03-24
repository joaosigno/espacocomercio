package net.danielfreire.products.ecommerce.model.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.danielfreire.util.GenericResponse;

public interface SiteBusiness {

	GenericResponse load(HttpServletRequest request) throws Exception;
	
	GenericResponse save(HttpServletRequest request) throws Exception;

	void upload(HttpServletRequest request, HttpServletResponse response) throws Exception;

}
