package net.danielfreire.products.ecommerce.model.core;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.ecommerce.model.domain.ProductCategory;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;

public interface ProductCategoryBusiness {
	
	GenericResponse insert(HttpServletRequest request) throws Exception;
	
	GenericResponse update(HttpServletRequest request) throws Exception;
	
	GenericResponse remove(HttpServletRequest request) throws Exception;
	
	GenericResponse load(HttpServletRequest request) throws Exception;
	
	GenericResponse list(HttpServletRequest request) throws Exception;
	
	List<ProductCategory> listSite(String cid) throws Exception;
	
	GridResponse consult(HttpServletRequest request) throws Exception;

}
