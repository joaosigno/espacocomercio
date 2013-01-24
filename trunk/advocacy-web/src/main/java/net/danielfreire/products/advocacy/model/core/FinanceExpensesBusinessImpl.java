package net.danielfreire.products.advocacy.model.core;

import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.advocacy.model.domain.FinanceCategory;
import net.danielfreire.products.advocacy.model.domain.FinanceExpenses;
import net.danielfreire.products.advocacy.model.repository.FinanceExpensesRepository;
import net.danielfreire.util.GridResponse;
import net.danielfreire.util.GridTitleResponse;
import net.danielfreire.util.PortalTools;
import net.danielfreire.util.ValidateTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component("financeExpensesBusiness")
public class FinanceExpensesBusinessImpl implements FinanceExpensesBusiness {
	
	@Autowired
	private FinanceExpensesRepository repository;

	@Override
	public GridResponse consult(HttpServletRequest request) {
		String page = request.getParameter("page");
		String idCategory = request.getParameter("cid");
		String month = request.getParameter("m");
		String year = request.getParameter("a");
		
		int pagination = 0;
		
		if (ValidateTools.getInstancia().isNumber(page)) {
			pagination = Integer.parseInt(page)-1;
		} 
		
		ArrayList<GridTitleResponse> titles = new ArrayList<GridTitleResponse>();
		titles.add(PortalTools.getInstance().getRowGrid("title", "Título", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("dateExpiration", "Vencimento", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("datePayment", "Pagamento", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("description", "Descrição", "text"));
		
		Calendar cInit = Calendar.getInstance();
		cInit.set(Integer.parseInt(year), Integer.parseInt(month)-1, 1);
		Calendar cEnd = Calendar.getInstance();
		cEnd.set(Integer.parseInt(year), Integer.parseInt(month), 1);
		cEnd.add(Calendar.DAY_OF_MONTH, -1);
		
		Page<FinanceExpenses> pageable = null;
		pageable = repository.findByCategoryAndDateExpirationGreaterThanAndDateExpirationLessThan(new FinanceCategory(Integer.parseInt(idCategory)), cInit, cEnd, new PageRequest(pagination, 10));
		
		return PortalTools.getInstance().getGrid(pageable.getContent(), titles, pageable.getNumber()+1, pageable.getTotalPages());
	}

}
