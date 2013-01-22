package net.danielfreire.products.ecommerce.model.core;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.danielfreire.products.ecommerce.model.repository.CategoryHasProductRepository;
import net.danielfreire.util.GenericResponse;

@Component("categoryHasProductBusiness")
public class CategoryHasProductBusinessImpl implements
		CategoryHasProductBusiness {
	
	@Autowired
	private CategoryHasProductRepository repository;

	@Override
	public GenericResponse insert(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenericResponse update(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenericResponse listProductByCategory(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
