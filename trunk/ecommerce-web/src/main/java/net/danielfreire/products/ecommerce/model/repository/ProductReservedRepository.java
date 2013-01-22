package net.danielfreire.products.ecommerce.model.repository;

import java.util.ArrayList;

import net.danielfreire.products.ecommerce.model.domain.ProductReserved;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReservedRepository extends JpaRepository<ProductReserved, Integer> {
	
	public ArrayList<ProductReserved> findByProductIdAndActive(Integer productId, Boolean active);

}
