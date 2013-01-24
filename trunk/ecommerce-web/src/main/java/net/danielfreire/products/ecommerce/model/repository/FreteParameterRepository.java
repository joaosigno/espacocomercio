package net.danielfreire.products.ecommerce.model.repository;

import net.danielfreire.products.ecommerce.model.domain.FreteParameter;
import net.danielfreire.products.ecommerce.model.domain.Site;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreteParameterRepository extends JpaRepository<FreteParameter, Integer> {
	
	public Page<FreteParameter> findBySite(Site site, Pageable p);
	
	public FreteParameter findBySiteAndState(Site site, String state);

}
