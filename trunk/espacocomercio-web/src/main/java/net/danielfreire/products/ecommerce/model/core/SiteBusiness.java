package net.danielfreire.products.ecommerce.model.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.danielfreire.util.GenericResponse;

public interface SiteBusiness {

	GenericResponse load(HttpServletRequest request) throws java.lang.Exception;
	
	GenericResponse save(HttpServletRequest request) throws java.lang.Exception;

	void upload(HttpServletRequest request, HttpServletResponse response) throws java.lang.Exception;
	
	Long countTotalSiteBySessionAdmin(HttpServletRequest request) throws java.lang.Exception;
	
	String getNormalizeNameSiteBySessionAdmin(HttpServletRequest request);

	GenericResponse update(HttpServletRequest request) throws java.lang.Exception;

}
