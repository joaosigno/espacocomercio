package net.danielfreire.products.ecommerce.model.repository;

import java.util.List;

import net.danielfreire.products.ecommerce.model.domain.Payment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
	
	public Page<Payment> findBySiteId(Integer siteId, Pageable p);
	
	public List<Payment> findBySiteId(Integer siteId);
	
	public Payment findByName(String name);

}
