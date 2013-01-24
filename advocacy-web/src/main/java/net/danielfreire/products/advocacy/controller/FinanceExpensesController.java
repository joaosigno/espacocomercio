package net.danielfreire.products.advocacy.controller;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.advocacy.model.core.FinanceExpensesBusiness;
import net.danielfreire.util.GridResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FinanceExpensesController {
	
	@Autowired
	private FinanceExpensesBusiness business;
	
	@RequestMapping(value="/admin/finance/expenses/consult", method = RequestMethod.GET)
	public @ResponseBody GridResponse consult(HttpServletRequest request) {
		return business.consult(request);
	}

}
