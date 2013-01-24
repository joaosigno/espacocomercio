package net.danielfreire.products.advocacy.model.core;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.advocacy.model.domain.FinanceCategory;
import net.danielfreire.products.advocacy.model.domain.FinanceExpenses;
import net.danielfreire.products.advocacy.model.repository.FinanceExpensesRepository;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;
import net.danielfreire.util.GridTitleResponse;
import net.danielfreire.util.PortalTools;
import net.danielfreire.util.ValidateTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
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
		titles.add(PortalTools.getInstance().getRowGrid("category", "Categoria", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("dateExpiration", "Vencimento", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("datePayment", "Pagamento", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("description", "Descrição", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("value", "Valor", "text"));
		
		Calendar cInit = Calendar.getInstance();
		cInit.set(Integer.parseInt(year), Integer.parseInt(month)-1, 1);
		Calendar cEnd = Calendar.getInstance();
		cEnd.set(Integer.parseInt(year), Integer.parseInt(month), 1);
		cEnd.add(Calendar.DAY_OF_MONTH, -1);
		
		Page<FinanceExpenses> pageable = null;
		pageable = repository.findByCategoryAndDateExpirationGreaterThanAndDateExpirationLessThan(new FinanceCategory(Integer.parseInt(idCategory)), cInit, cEnd, new PageRequest(pagination, 10, Direction.DESC, "dateExpiration"));
		
		return PortalTools.getInstance().getGrid(pageable.getContent(), titles, pageable.getNumber()+1, pageable.getTotalPages());
	}

	@SuppressWarnings("unchecked")
	@Override
	public GenericResponse manage(HttpServletRequest request) throws Exception {
		GenericResponse resp = new GenericResponse();
		
		HashMap<String, Object> ret = getExpenses(request);
		
		if (ret.get("errors")!=null) {
			HashMap<String, String> errors = (HashMap<String, String>) ret.get("errors");
			resp = PortalTools.getInstance().getRespError(errors);
		} else {
			FinanceExpenses expense = (FinanceExpenses) ret.get("expense");
			repository.save(expense);
		}
		
		return resp;
	}
	
	private HashMap<String, Object> getExpenses(HttpServletRequest request) throws Exception {
		HashMap<String, Object> retorno = new HashMap<String, Object>();
		
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String dateExpiration = request.getParameter("dateExpiration");
		String datePayment = request.getParameter("datePayment");
		String description = request.getParameter("description");
		String category = request.getParameter("category");
		String value = request.getParameter("value").replace(",", ".");
		
		HashMap<String, String> errors = new HashMap<String, String>();
		if (ValidateTools.getInstancia().isNullEmpty(title)) {
			errors.put("title", PortalTools.getInstance().getMessage("title.invalid"));
		}
		if (!ValidateTools.getInstancia().isStringDate(dateExpiration)) {
			errors.put("dateExpiration", PortalTools.getInstance().getMessage("dateexpiration.invalid"));
		}
		if (ValidateTools.getInstancia().isNotnull(datePayment) && !ValidateTools.getInstancia().isStringDate(datePayment)) {
			errors.put("datePayment", PortalTools.getInstance().getMessage("datepayment.invalid"));
		}
		if (!ValidateTools.getInstancia().isDouble(value)) {
			errors.put("value", PortalTools.getInstance().getMessage("value.invalid"));
		}
		
		if (errors.size()==0) {
			FinanceExpenses expense = new FinanceExpenses();
			if (ValidateTools.getInstancia().isNumber(id)) {
				expense = new FinanceExpenses(Integer.parseInt(id));
			}
			
			if (ValidateTools.getInstancia().isNotnull(datePayment)) {
				Calendar datePaymentCalendar = Calendar.getInstance();
				datePaymentCalendar.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(datePayment));
				expense.setDatePayment(datePaymentCalendar);
			}
			
			Calendar dateExpirationCalendar = Calendar.getInstance();
			dateExpirationCalendar.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(dateExpiration));
			expense.setDateExpiration(dateExpirationCalendar);
			
			expense.setCategory(new FinanceCategory(Integer.parseInt(category)));
			expense.setDescription(description);
			expense.setTitle(title);
			expense.setValue(Double.parseDouble(value));
			
			retorno.put("expense", expense);
		} else {
			retorno.put("errors", errors);
		}
		
		return retorno;
	}

}
