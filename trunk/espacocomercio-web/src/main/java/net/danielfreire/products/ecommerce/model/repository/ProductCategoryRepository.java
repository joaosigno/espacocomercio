package net.danielfreire.products.ecommerce.model.repository;

import java.util.List;

import net.danielfreire.products.ecommerce.model.domain.ProductCategory;
import net.danielfreire.products.ecommerce.model.domain.Site;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
	
	Page<ProductCategory> findBySite(Site site, Pageable pageable);
	
	List<ProductCategory> findBySite(Site site);
	
	ProductCategory findByNameAndSite(String name, Site site);
	
	ProductCategory findByKeyUrlAndSite(String keyUrl, Site site);

	@Query("select count(id) from ProductCategory where site = ?1")
	Long countBySite(Site site);

}
