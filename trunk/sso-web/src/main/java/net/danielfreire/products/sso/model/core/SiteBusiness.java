package net.danielfreire.products.sso.model.core;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;

public interface SiteBusiness {
	
	GenericResponse update(String nome, String desc, HttpServletRequest request);
	
	GenericResponse insert(HttpServletRequest request);

	GridResponse consult(HttpServletRequest request);
	
	GenericResponse update(HttpServletRequest request);
	
	GenericResponse remove(HttpServletRequest request);
	
}
