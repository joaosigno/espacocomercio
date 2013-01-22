package net.danielfreire.products.ecommerce.model.core;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;

public interface ClientEcommerceBusiness {
	
	GenericResponse updateAdmin(HttpServletRequest request) throws Exception;
	
	GenericResponse list(HttpServletRequest request) throws Exception;
	
	GridResponse consult(HttpServletRequest request) throws Exception;
	
	GenericResponse insert(HttpServletRequest request) throws Exception;
	
	GenericResponse login(HttpServletRequest request) throws Exception;
	
	GenericResponse forgotPassword(HttpServletRequest request) throws Exception;
	
	GenericResponse updatePassword(HttpServletRequest request) throws Exception;
	
	GenericResponse updateAddress(HttpServletRequest request) throws Exception;
	
	GenericResponse updateData(HttpServletRequest request) throws Exception;
	
	HashMap<String, Object> portalSession(HttpServletRequest request, String sid) throws Exception;
	
	void clearSession(HttpServletRequest request) throws Exception;
}
