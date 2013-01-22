package net.danielfreire.products.advocacy.controller;

import net.danielfreire.products.advocacy.model.core.FinanceCategoryBusiness;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class FinanceCategoryController {

	@Autowired
	private FinanceCategoryBusiness business;
}
