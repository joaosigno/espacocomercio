package net.danielfreire.products.advocacy.model.core;

import net.danielfreire.products.advocacy.model.repository.FinanceCategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("financeCategoryBusiness")
public class FinanceCategoryBusinessImpl implements FinanceCategoryBusiness {
	
	@Autowired
	private FinanceCategoryRepository repository;
}
