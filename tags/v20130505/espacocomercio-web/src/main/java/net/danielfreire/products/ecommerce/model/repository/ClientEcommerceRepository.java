package net.danielfreire.products.ecommerce.model.repository;

import java.util.Calendar;
import java.util.List;

import net.danielfreire.products.ecommerce.model.domain.ClientEcommerce;
import net.danielfreire.products.ecommerce.model.domain.Site;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientEcommerceRepository extends JpaRepository<ClientEcommerce, Integer> {
	
	Page<ClientEcommerce> findBySite(Site site, Pageable pageable);
	
	@Query("select p from ClientEcommerce p where p.site = ?1 and (p.name like ?2 OR p.user like ?2 OR p.addressStreet like ?2 OR p.addressCity like ?2)")
	Page<ClientEcommerce> findBySite(Site site, String filter, Pageable pageable);
	
	List<ClientEcommerce> findBySite(Site site);
	
	List<ClientEcommerce> findBySiteAndCreationDateGreaterThanAndCreationDateLessThan(Site site, Calendar dtIni, Calendar dtEnd);
	
	@Query("select count(id) from ClientEcommerce where site = ?1")
	Long countBySite(Site site);
	
	ClientEcommerce findBySiteAndUser(Site site, String user);
	
	List<ClientEcommerce> findByUserAndPassword(String user, String password);

	ClientEcommerce findBySiteAndUserAndPassword(Site site, String user, String password);

	@Query("select count(id) from ClientEcommerce where site = ?1 and creationDate >= ?2")
	Long countBySiteAndCreationDateGreaterThan(Site site, Calendar dtInit);

}
