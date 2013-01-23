package net.danielfreire.products.advocacy.model.repository;

import net.danielfreire.products.advocacy.model.domain.FinanceCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinanceCategoryRepository extends JpaRepository<FinanceCategory, Integer> {
	
	Page<FinanceCategory> findByAdvocacyOffice(Integer advocacyOffice, Pageable pageable);
	
}
