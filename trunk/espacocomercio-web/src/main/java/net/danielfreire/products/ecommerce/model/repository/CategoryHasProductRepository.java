package net.danielfreire.products.ecommerce.model.repository;

import java.util.List;

import net.danielfreire.products.ecommerce.model.domain.CategoryHasProduct;
import net.danielfreire.products.ecommerce.model.domain.ProductCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryHasProductRepository extends JpaRepository<CategoryHasProduct, Integer> {
	
	public List<CategoryHasProduct> findByProductId(Integer productId);
	
	public Page<CategoryHasProduct> findByCategory(ProductCategory category, Pageable p);

}
