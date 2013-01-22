package net.danielfreire.products.ecommerce.model.core;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.ecommerce.model.repository.ProductReservedRepository;
import net.danielfreire.util.GenericResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("productReservedBusiness")
public class ProductReservedBusinessImpl implements ProductReservedBusiness {

	@Autowired
	private ProductReservedRepository repository;
	
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
	public GenericResponse listReserved(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenericResponse productIsReserved(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
