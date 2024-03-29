package net.danielfreire.products.ecommerce.model.repository;

import java.util.List;

import net.danielfreire.products.ecommerce.model.domain.Product;
import net.danielfreire.products.ecommerce.model.domain.Site;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	Page<Product> findBySite(Site site, Pageable pageable);
	
	@Query("select p from Product p where p.site = ?1 and (p.name like ?2 OR p.introduction like ?2 OR p.description like ?2)")
	Page<Product> findBySite(Site site, String filter, Pageable pageRequest);
	
	List<Product> findBySite(Site site);
	
	Product findByKeyUrlAndSite(String keyUrl, Site site);
	
	Page<Product> findBySiteAndDescriptionLike(Site site, String description, Pageable pageable);

	@Query("select count(id) from Product where site = ?1")
	Long countBySite(Site site);

	@Query("select count(id) from Product where site = ?1 and quantity = ?2")
	Long countBySiteAndQuantity(Site site, Double qtd);
	
	Page<Product> findBySiteAndQuantity(Site site, Double qtd, Pageable pageable);

	@Query("select sum(quantity) from Product where site = ?1")
	Double countQuantityBySite(Site site);

}
