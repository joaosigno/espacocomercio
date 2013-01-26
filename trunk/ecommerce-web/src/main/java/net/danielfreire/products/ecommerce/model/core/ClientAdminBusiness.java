package net.danielfreire.products.ecommerce.model.core;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.util.GenericResponse;

public interface ClientAdminBusiness {
	
	GenericResponse login(HttpServletRequest request) throws Exception;

	GenericResponse menu(HttpServletRequest request) throws Exception;

}
