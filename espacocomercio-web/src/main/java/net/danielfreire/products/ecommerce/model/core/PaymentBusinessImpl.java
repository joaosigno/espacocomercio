package net.danielfreire.products.ecommerce.model.core;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.ecommerce.model.domain.Payment;
import net.danielfreire.products.ecommerce.model.repository.PaymentRepository;
import net.danielfreire.products.ecommerce.util.EcommerceUtil;
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
			payment.setSite(EcommerceUtil.getInstance().getSessionAdmin(request).getSite());
			payment.setDescription(description);
			payment.setUrl(url);
			
			resp.put("errors", null);
			resp.put("payment", payment);
		}
		
		return resp;
	}

	@Override
	public GridResponse consult(HttpServletRequest request) throws Exception {
		String page = request.getParameter("page");
		
		int pagination = 0;
		
		if (ValidateTools.getInstancia().isNumber(page)) {
			pagination = Integer.parseInt(page)-1;
		} 
		
		ArrayList<GridTitleResponse> titles = new ArrayList<GridTitleResponse>();
		titles.add(PortalTools.getInstance().getRowGrid("name", "Nome", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("description", "Descrição", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("url", "Token", "text"));
		
		Page<Payment> pageable = repository.findBySite(EcommerceUtil.getInstance().getSessionAdmin(request).getSite(), new PageRequest(pagination, 10));
		
		return PortalTools.getInstance().getGrid(pageable.getContent(), titles, pageable.getNumber()+1, pageable.getTotalPages());
	}

	@Override
	public GenericResponse remove(HttpServletRequest request) throws Exception {
		Payment p = repository.findOne(Integer.parseInt(request.getParameter("id")));
		if (p.getSite().getId()==EcommerceUtil.getInstance().getSessionAdmin(request).getSite().getId()) {
			repository.delete(p);
		}
		
		return new GenericResponse();
	}

	@Override
	public GenericResponse list(HttpServletRequest request) throws Exception {
		GenericResponse resp = new GenericResponse();
		resp.setGenericList(repository.findBySite(EcommerceUtil.getInstance().getSessionAdmin(request).getSite()));
		
		return resp;
	}

}
