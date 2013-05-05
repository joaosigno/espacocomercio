package net.danielfreire.products.ecommerce.model.core;

import net.danielfreire.products.ecommerce.model.repository.CategoryHasProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("categoryHasProductBusiness")
public class CategoryHasProductBusinessImpl implements
		CategoryHasProductBusiness {
	
	@Autowired
	private CategoryHasProductRepository repository;

}
