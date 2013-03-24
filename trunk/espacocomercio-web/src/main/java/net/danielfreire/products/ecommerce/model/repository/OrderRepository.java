package net.danielfreire.products.ecommerce.model.repository;

import net.danielfreire.products.ecommerce.model.domain.ClientEcommerce;
import net.danielfreire.products.ecommerce.model.domain.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	public Page<Order> findByClient(ClientEcommerce client, Pageable p);
	
	public Page<Order> findByClientAndStatusOrderLessThan(ClientEcommerce client, Integer statusOrderLessThan, Pageable p);
	
	public Page<Order> findByClientAndStatusOrderGreaterThan(ClientEcommerce client, Integer statusOrderLessThan, Pageable p);
	
}
