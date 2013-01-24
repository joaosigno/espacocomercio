package net.danielfreire.products.advocacy.model.repository;

import java.util.Calendar;

import net.danielfreire.products.advocacy.model.domain.FinanceCategory;
import net.danielfreire.products.advocacy.model.domain.FinanceExpenses;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinanceExpensesRepository extends JpaRepository<FinanceExpenses, Integer> {
	
	Page<FinanceExpenses> findByCategoryAndDateExpirationGreaterThanAndDateExpirationLessThan(FinanceCategory category, Calendar dtInit, Calendar dtEnd, Pageable pageable);

}
