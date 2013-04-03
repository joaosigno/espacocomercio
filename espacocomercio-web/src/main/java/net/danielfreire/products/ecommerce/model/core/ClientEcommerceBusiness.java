package net.danielfreire.products.ecommerce.model.core;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.danielfreire.util.GenericResponse;

public interface ClientEcommerceBusiness {
	
	GenericResponse insert(HttpServletRequest request) throws java.lang.Exception;
	
	GenericResponse login(HttpServletRequest request) throws java.lang.Exception;
	
	GenericResponse forgotPassword(HttpServletRequest request) throws java.lang.Exception;
	
	GenericResponse updatePassword(HttpServletRequest request) throws java.lang.Exception;
	
	GenericResponse updateAddress(HttpServletRequest request) throws java.lang.Exception;
	
	GenericResponse updateData(HttpServletRequest request) throws java.lang.Exception;
	
	Map<String, Object> portalSession(HttpServletRequest request, String sid) throws java.lang.Exception;
	
	void clearSession(HttpServletRequest request) throws java.lang.Exception;

	void active(HttpServletRequest request, HttpServletResponse response) throws java.lang.Exception;
}
