package net.danielfreire.products.advocacy.model.repository;

import net.danielfreire.products.advocacy.model.domain.FinanceExpenses;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FinanceExpensesRepository extends JpaRepository<FinanceExpenses, Integer> {

}
