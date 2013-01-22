package net.danielfreire.products.ecommerce.model.repository;

import java.util.List;

import net.danielfreire.products.ecommerce.model.domain.ProductCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
	
	public Page<ProductCategory> findBySiteId(Integer siteId, Pageable p);
	
	public List<ProductCategory> findBySiteId(Integer siteId);
	
	public ProductCategory findByNameAndSiteId(String name, Integer siteId);
	
	public ProductCategory findByKeyUrlAndSiteId(String keyUrl, Integer siteId);

}
