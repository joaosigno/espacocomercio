package net.danielfreire.products.advocacy.model.core;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;

public interface AdvocacyUserBusiness {
	
	GenericResponse login(HttpServletRequest request) throws Exception;
	
	GenericResponse menuAdmin(HttpServletRequest request) throws Exception;
	
	GenericResponse manage(HttpServletRequest request) throws Exception;
	
	GridResponse consult(HttpServletRequest request);
	
	GenericResponse load(HttpServletRequest request) throws Exception;

}
