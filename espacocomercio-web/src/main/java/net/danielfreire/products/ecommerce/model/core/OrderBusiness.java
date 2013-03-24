package net.danielfreire.products.ecommerce.model.core;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;

public interface OrderBusiness {
	
	GenericResponse update(HttpServletRequest request) throws Exception;
	
	GridResponse consult(HttpServletRequest request) throws Exception;
	
	GenericResponse listByOpt(HttpServletRequest request) throws Exception;
	
	GenericResponse insert(HttpServletRequest request, HashMap<String, Object> map) throws Exception;
}
