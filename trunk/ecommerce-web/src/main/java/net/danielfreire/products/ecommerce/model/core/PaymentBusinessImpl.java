package net.danielfreire.products.ecommerce.model.core;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.ecommerce.model.domain.Payment;
import net.danielfreire.products.ecommerce.model.repository.PaymentRepository;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;
import net.danielfreire.util.GridTitleResponse;
import net.danielfreire.util.PortalTools;
import net.danielfreire.util.ValidateTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component("paymentBusiness")
public class PaymentBusinessImpl implements PaymentBusiness {

	@Autowired
	private PaymentRepository repository;
	
	@SuppressWarnings("unchecked")
	@Override
	public GenericResponse insertUpdate(HttpServletRequest request) {
		GenericResponse resp = new GenericResponse();
		
		HashMap<String, Object> map = getPayment(request);
		
		if (map.get("errors")!=null) {
			resp = PortalTools.getInstance().getRespError((HashMap<String, String>) map.get("errors"));
		} else {
			repository.save((Payment) map.get("payment"));
		}
		
		return resp;
	}

	private HashMap<String, Object> getPayment(HttpServletRequest request) {
		final Integer siteId = Integer.parseInt(request.getSession().getAttribute(PortalTools.getInstance().idAdminSession).toString());
		final String name = request.getParameter("name");
		final String description = request.getParameter("description");
		final String url = request.getParameter("url");
		final String id = request.getParameter("id");
		
		HashMap<String, String> errors = new HashMap<String, String>();
		
		if (ValidateTools.getInstancia().isNullEmpty(name)) {
			errors.put("name", PortalTools.getInstance().getMessage("name.invalid"));
		}
		
		HashMap<String, Object> resp = new HashMap<String, Object>();
		if (errors.size()>0) {
			resp.put("errors", errors);
			resp.put("client", null);
		} else {
			Payment payment = new Payment();
			if (ValidateTools.getInstancia().isNumber(id)) {
				payment = new Payment(Integer.parseInt(id));
			}
			payment.setName(name);
			payment.setSiteId(siteId);
			payment.setDescription(description);
			payment.setUrl(url);
			
			resp.put("errors", null);
			resp.put("payment", payment);
		}
		
		return resp;
	}

	@Override
	public GridResponse consult(HttpServletRequest request) throws Exception {
		String siteId = (String) request.getSession().getAttribute(PortalTools.getInstance().idAdminSession);
		String page = request.getParameter("page");
		
		int pagination = 0;
		
		if (ValidateTools.getInstancia().isNumber(page)) {
			pagination = Integer.parseInt(page)-1;
		} 
		
		ArrayList<GridTitleResponse> titles = new ArrayList<GridTitleResponse>();
		
		GridTitleResponse title = new GridTitleResponse();
		title.setId("name");
		title.setTitle("Nome");
		title.setType("text");
		titles.add(title);
		
		title = new GridTitleResponse();
		title.setId("description");
		title.setTitle("Descrição");
		title.setType("text");
		titles.add(title);
		
		title = new GridTitleResponse();
		title.setId("url");
		title.setTitle("Token");
		title.setType("text");
		titles.add(title);
		
		Page<Payment> pageable = repository.findBySiteId(Integer.parseInt(siteId), new PageRequest(pagination, 10));
		
		GridResponse grid = new GridResponse();
		grid.setRows(pageable.getContent());
		grid.setTitles(titles);
		grid.setPage(pageable.getNumber()+1);
		grid.setTotalPages(pageable.getTotalPages());
		
		return grid;
	}

	@Override
	public GenericResponse remove(HttpServletRequest request) throws Exception {
		repository.delete(Integer.parseInt(request.getParameter("id")));
		return new GenericResponse();
	}

	@Override
	public GenericResponse list(HttpServletRequest request) throws Exception {
		GenericResponse resp = new GenericResponse();
		resp.setGenericList(repository.findBySiteId(Integer.parseInt(request.getSession().getAttribute(PortalTools.getInstance().idAdminSession).toString())));
		
		return resp;
	}

}
