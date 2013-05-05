package net.danielfreire.products.ecommerce.model.core;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;

public interface ClientForAdminBusiness {
	
	GridResponse consult(HttpServletRequest request) throws java.lang.Exception;
	
	GenericResponse updateAdmin(HttpServletRequest request) throws java.lang.Exception;
	
	Long countClients(HttpServletRequest request);
	
	GenericResponse list(HttpServletRequest request) throws java.lang.Exception;
	
	Integer[] getListClientByLastMonths(HttpServletRequest request);
	
	Long countLastClientsRegistry(HttpServletRequest request);

}
