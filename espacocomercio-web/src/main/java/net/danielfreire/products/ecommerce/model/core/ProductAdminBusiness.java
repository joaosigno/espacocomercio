package net.danielfreire.products.ecommerce.model.core;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.danielfreire.products.ecommerce.model.domain.Product;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;

public interface ProductAdminBusiness {
	
	GenericResponse insertUpdate(HttpServletRequest request) throws java.lang.Exception;
	
	GenericResponse detele(HttpServletRequest request) throws java.lang.Exception;
	
	void upload(HttpServletRequest request, HttpServletResponse response) throws java.lang.Exception;
	
	GridResponse consult(HttpServletRequest request) throws java.lang.Exception;
	
	Long countProducts(HttpServletRequest request);
	
	Long countProductsSemEstoque(HttpServletRequest request);

	List<Product> listProductsSemEstoque(HttpServletRequest request);

	List<Product> listProductsOrderByEstoque(HttpServletRequest request);

	Double countQuantityAllProducts(HttpServletRequest request);

}
