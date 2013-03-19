package net.danielfreire.products.ecommerce.model.repository;

import java.util.List;

import net.danielfreire.products.ecommerce.model.domain.ProductCategory;
import net.danielfreire.products.ecommerce.model.domain.Site;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
	
	public Page<ProductCategory> findBySite(Site site, Pageable p);
	
	public List<ProductCategory> findBySite(Site site);
	
	public ProductCategory findByNameAndSite(String name, Site site);
	
	public ProductCategory findByKeyUrlAndSite(String keyUrl, Site site);

}
