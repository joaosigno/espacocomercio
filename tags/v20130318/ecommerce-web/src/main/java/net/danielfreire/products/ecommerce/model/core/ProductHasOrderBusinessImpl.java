package net.danielfreire.products.ecommerce.model.core;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.danielfreire.products.ecommerce.model.repository.ProductHasOrderRepository;
import net.danielfreire.util.GenericResponse;

@Component("productHasOrderBusiness")
public class ProductHasOrderBusinessImpl implements ProductHasOrderBusiness {
	
	@Autowired
	private ProductHasOrderRepository repository;

	@Override
	public GenericResponse insert(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenericResponse update(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenericResponse list(HttpServletRequest request) {
		GenericResponse resp = new GenericResponse();
		resp.setGenericList(repository.findByOrderId(Integer.parseInt(request.getParameter("oid"))));
		return resp;
	}

}
