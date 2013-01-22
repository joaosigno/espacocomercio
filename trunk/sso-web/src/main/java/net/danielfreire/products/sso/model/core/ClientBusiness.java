package net.danielfreire.products.sso.model.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.danielfreire.products.sso.model.domain.Client;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;

public interface ClientBusiness {

	GenericResponse insert(HttpServletRequest request);
	GenericResponse insert(Client client);
	
	GenericResponse update(HttpServletRequest request);
	GenericResponse updateAdmin(HttpServletRequest request) throws Exception;
	
	GenericResponse remove(HttpServletRequest request);
	
	GenericResponse list(HttpServletRequest request);
	
	GenericResponse login(String user, String password, HttpServletRequest request);
	
	GenericResponse load(HttpServletRequest request);
	
	GridResponse consult(HttpServletRequest request);
	
	void logout(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	GenericResponse session(HttpServletRequest request) throws Exception;
}
