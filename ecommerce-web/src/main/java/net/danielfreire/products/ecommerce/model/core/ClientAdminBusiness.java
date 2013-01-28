package net.danielfreire.products.ecommerce.model.core;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;

public interface ClientAdminBusiness {
	
	GenericResponse login(HttpServletRequest request) throws Exception;

	GenericResponse menu(HttpServletRequest request) throws Exception;

	GridResponse consult(HttpServletRequest request) throws Exception;

	GenericResponse save(HttpServletRequest request) throws Exception;

	GenericResponse load(HttpServletRequest request) throws Exception;

	GenericResponse remove(HttpServletRequest request) throws Exception;

}
