package net.danielfreire.products.ecommerce.model.repository;

import java.util.Calendar;
import java.util.List;

import net.danielfreire.products.ecommerce.model.domain.ClientEcommerce;
import net.danielfreire.products.ecommerce.model.domain.Order;
import net.danielfreire.products.ecommerce.model.domain.Site;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	Page<Order> findByClient(ClientEcommerce client, Pageable pageable);
	
	Page<Order> findByClientAndStatusOrderLessThan(ClientEcommerce client, Integer statusOrder, Pageable pageable);
	
	Page<Order> findByClientAndStatusOrderGreaterThan(ClientEcommerce client, Integer statusOrder, Pageable pageable);

	List<Order> findByClient(ClientEcommerce client);
	
	@Query("select count(id) from Order where client.id in (select id from ClientEcommerce where site = ?1)")
	Long countBySite(Site site);
	
	@Query("from Order where dateCreate >= ?2 and dateCreate <= ?3 and client.id in (select id from ClientEcommerce where site = ?1)")
	List<Order> findBySite(Site site, Calendar dtIni, Calendar dtEnd);

	@Query("select count(id) from Order where dateCreate >= ?2 and client.id in (select id from ClientEcommerce where site = ?1)")
	Long countBySiteAndDateCreateGreaterThan(Site site, Calendar dtInit);

	@Query("select count(id) from Order where statusOrder = ?2 and client.id in (select id from ClientEcommerce where site = ?1)")
	Long countBySiteAndStatusOrder(Site site, Integer status);
	
	@Query("from Order where client.id in (select id from ClientEcommerce where site = ?1) order by dateCreate desc")
	List<Order> findLastOrdersBySite(Site site, Pageable pageable);
}
