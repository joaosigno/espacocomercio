package net.danielfreire.products.advocacy.model.core;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.util.GenericResponse;

public interface AdvocacyClientBusiness {
	
	GenericResponse save(HttpServletRequest request) throws Exception; 

}
