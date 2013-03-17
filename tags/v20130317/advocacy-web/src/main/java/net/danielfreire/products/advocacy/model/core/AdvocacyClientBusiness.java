package net.danielfreire.products.advocacy.model.core;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;

public interface AdvocacyClientBusiness {
	
	GenericResponse save(HttpServletRequest request) throws Exception;

	GridResponse consult(HttpServletRequest request) throws Exception;

	GenericResponse load(HttpServletRequest request) throws Exception;

	GenericResponse remove(HttpServletRequest request) throws Exception;

}
