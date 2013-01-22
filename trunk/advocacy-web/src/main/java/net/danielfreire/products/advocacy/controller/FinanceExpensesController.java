package net.danielfreire.products.advocacy.controller;

import net.danielfreire.products.advocacy.model.core.FinanceExpensesBusiness;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class FinanceExpensesController {
	
	@Autowired
	private FinanceExpensesBusiness business;

}
