package net.danielfreire.products.ecommerce.model.repository;

import net.danielfreire.products.ecommerce.model.domain.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	public Page<Product> findBySiteId(Integer siteId, Pageable p);
	
	public Product findByKeyUrlAndSiteId(String keyUrl, Integer siteId);
	
}
