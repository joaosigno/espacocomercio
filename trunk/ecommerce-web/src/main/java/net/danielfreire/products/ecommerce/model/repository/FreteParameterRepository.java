package net.danielfreire.products.ecommerce.model.repository;

import net.danielfreire.products.ecommerce.model.domain.FreteParameter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreteParameterRepository extends JpaRepository<FreteParameter, Integer> {
	
	public Page<FreteParameter> findBySiteId(Integer siteId, Pageable p);
	
	public FreteParameter findBySiteIdAndState(Integer siteId, String state);

}
