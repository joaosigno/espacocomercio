package net.danielfreire.products.ecommerce.model.core;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.ecommerce.model.domain.Order;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;

public interface OrderBusiness {
	
	int ID_STS_AGUARD_PG = 1;
	int ID_STS_PG_CONFIRM = 2;
	int ID_STS_ENVIADO = 3;
	int ID_STS_FINALIZADO = 4;
	int ID_STS_CANCELADO = 5;

	GenericResponse update(HttpServletRequest request) throws java.lang.Exception;
	
	GridResponse consult(HttpServletRequest request) throws java.lang.Exception;
	
	GenericResponse listByOpt(HttpServletRequest request) throws java.lang.Exception;
	
	GenericResponse insert(HttpServletRequest request, Map<String, Object> map) throws java.lang.Exception;
	
	Long countOrders(HttpServletRequest request);
	
	Integer[] getListOrdersByLastMonths(HttpServletRequest request);
	
	Long countLastOrders(HttpServletRequest request);
	
	Long countLastOrdersPayment(HttpServletRequest request);

	List<Order> listLastOrders(HttpServletRequest request);
	
	Long countOrderByStatus(int status, HttpServletRequest request);
}
