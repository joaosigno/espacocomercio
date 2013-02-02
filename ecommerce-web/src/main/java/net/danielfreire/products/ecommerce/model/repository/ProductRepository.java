package net.danielfreire.products.ecommerce.model.repository;

import net.danielfreire.products.ecommerce.model.domain.Product;
import net.danielfreire.products.ecommerce.model.domain.Site;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	public Page<Product> findBySite(Site site, Pageable p);
	
	public Product findByKeyUrlAndSite(String keyUrl, Site site);
	
	public Page<Product> findBySiteAndDescriptionLike(Site site, String description, Pageable p);
}
