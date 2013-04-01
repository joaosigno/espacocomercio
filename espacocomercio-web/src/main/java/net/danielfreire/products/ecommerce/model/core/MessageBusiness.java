package net.danielfreire.products.ecommerce.model.core;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.util.GenericResponse;

public interface MessageBusiness {

	GenericResponse newMessage(HttpServletRequest request) throws Exception;
	
	GenericResponse countLastMessage(HttpServletRequest request);
}
