package net.danielfreire.products.ecommerce.model.core;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.ecommerce.model.domain.Order;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;

public interface OrderBusiness {
	
	GenericResponse update(HttpServletRequest request) throws java.lang.Exception;
	
	GridResponse consult(HttpServletRequest request) throws java.lang.Exception;
	
	GenericResponse listByOpt(HttpServletRequest request) throws java.lang.Exception;
	
	GenericResponse insert(HttpServletRequest request, Map<String, Object> map) throws java.lang.Exception;
	
	Long countOrders(HttpServletRequest request);
	
	Integer[] getListOrdersByLastMonths(HttpServletRequest request);
	
	Long countLastOrders(HttpServletRequest request);
	
	Long countLastOrdersPayment(HttpServletRequest request);

	List<Order> listLastOrders(HttpServletRequest request);
}
