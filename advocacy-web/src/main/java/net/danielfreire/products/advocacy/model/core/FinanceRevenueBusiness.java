package net.danielfreire.products.advocacy.model.core;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;

public interface FinanceRevenueBusiness {

	GridResponse consult(HttpServletRequest request);

	GenericResponse manage(HttpServletRequest request) throws Exception;

	GenericResponse remove(HttpServletRequest request) throws Exception;

}
