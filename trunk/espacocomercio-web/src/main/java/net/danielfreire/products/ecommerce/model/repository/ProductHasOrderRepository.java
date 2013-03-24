package net.danielfreire.products.ecommerce.model.repository;

import java.util.List;

import net.danielfreire.products.ecommerce.model.domain.ProductHasOrder;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductHasOrderRepository extends JpaRepository<ProductHasOrder, Integer> {
	
	List<ProductHasOrder> findByOrderId(Integer orderId);

}
