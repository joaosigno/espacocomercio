package net.danielfreire.products.advocacy.model.core;

import net.danielfreire.products.advocacy.model.repository.FinanceExpensesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("financeExpensesBusiness")
public class FinanceExpensesBusinessImpl implements FinanceExpensesBusiness {
	
	@Autowired
	private FinanceExpensesRepository repository;

}
