package net.danielfreire.products.advocacy.model.core;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;

public interface AdvocacyOfficeBusiness {

	GenericResponse manage(HttpServletRequest request) throws Exception;
	
	GridResponse consult(HttpServletRequest request);
	
	GenericResponse listBySession(HttpServletRequest request) throws Exception;
}
