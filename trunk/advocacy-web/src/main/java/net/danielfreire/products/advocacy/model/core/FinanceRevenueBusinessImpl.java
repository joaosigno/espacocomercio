package net.danielfreire.products.advocacy.model.core;

import net.danielfreire.products.advocacy.model.repository.FinanceRevenueRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("financeRevenueBusiness")
public class FinanceRevenueBusinessImpl implements FinanceRevenueBusiness {

	@Autowired
	private FinanceRevenueRepository repository;
}
