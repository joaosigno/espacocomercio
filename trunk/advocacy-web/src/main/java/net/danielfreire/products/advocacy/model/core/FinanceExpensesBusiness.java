package net.danielfreire.products.advocacy.model.core;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.util.GridResponse;

public interface FinanceExpensesBusiness {
	
	GridResponse consult(HttpServletRequest request);
	
}
