package net.danielfreire.products.ecommerce.model.core;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;

public interface PaymentBusiness {
	
	GenericResponse insertUpdate(HttpServletRequest request) throws Exception;
	
	GenericResponse remove(HttpServletRequest request) throws Exception;
	
	GridResponse consult(HttpServletRequest request) throws Exception;
	
	GenericResponse list(HttpServletRequest request) throws Exception;

}

