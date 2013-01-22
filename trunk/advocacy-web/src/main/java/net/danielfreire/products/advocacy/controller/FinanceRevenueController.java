package net.danielfreire.products.advocacy.controller;

import net.danielfreire.products.advocacy.model.core.FinanceRevenueBusiness;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class FinanceRevenueController {
	
	@Autowired
	private FinanceRevenueBusiness business;

}
