package net.danielfreire.products.ecommerce.model.core;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.util.GenericResponse;

public interface MessageBusiness {

	GenericResponse newMessage(HttpServletRequest request) throws java.lang.Exception;
	
	void createMessage(final String name, final String type, final String email,
			final String text, final String origin, final Integer sid) throws java.lang.Exception;
	
	Long countLastMessage(HttpServletRequest request);
}
