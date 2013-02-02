package net.danielfreire.products.ecommerce.model.core;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;

public interface FreteParameterBusiness {
	
	GenericResponse insertUpdate(HttpServletRequest request) throws Exception;
	
	GridResponse consult(HttpServletRequest request) throws Exception;
	
	GenericResponse getFrete(HttpServletRequest request) throws Exception;

	GenericResponse delivery(HttpServletRequest request) throws Exception;

}
