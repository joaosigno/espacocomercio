package net.danielfreire.products.advocacy.model.repository;

import net.danielfreire.products.advocacy.model.domain.FinanceCategory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FinanceCategoryRepository extends JpaRepository<FinanceCategory, Integer> {

}
