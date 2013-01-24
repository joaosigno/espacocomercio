package net.danielfreire.products.advocacy.model.repository;

import java.util.Calendar;

import net.danielfreire.products.advocacy.model.domain.FinanceCategory;
import net.danielfreire.products.advocacy.model.domain.FinanceRevenue;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinanceRevenueRepository extends JpaRepository<FinanceRevenue, Integer> {

	Page<FinanceRevenue> findByCategoryAndDatePaymentGreaterThanAndDatePaymentLessThan(FinanceCategory financeCategory, Calendar cInit, Calendar cEnd, Pageable pageable);

}
