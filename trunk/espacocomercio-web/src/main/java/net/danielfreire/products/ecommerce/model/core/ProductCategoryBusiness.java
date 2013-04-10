package net.danielfreire.products.ecommerce.model.core;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.ecommerce.model.domain.ProductCategory;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;

public interface ProductCategoryBusiness {
	
	GenericResponse insert(HttpServletRequest request) throws java.lang.Exception;
	
	GenericResponse update(HttpServletRequest request) throws java.lang.Exception;
	
	GenericResponse remove(HttpServletRequest request) throws java.lang.Exception;
	
	GenericResponse list(HttpServletRequest request) throws java.lang.Exception;
	
	List<ProductCategory> listSite(String cid) throws java.lang.Exception;
	
	GridResponse consult(HttpServletRequest request) throws java.lang.Exception;

	Long countCategorys(HttpServletRequest request);

}
