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
	
	public Page<ClientEcommerce> findBySite(Site site, Pageable p);
	
	public List<ClientEcommerce> findBySite(Site site);
	
	public List<ClientEcommerce> findBySiteAndCreationDateGreaterThanAndCreationDateLessThan(Site site, Calendar dtIni, Calendar dtEnd);
	
	@Query("select count(id) from ClientEcommerce where site = ?1")
	public Long countBySite(Site site);
	
	public ClientEcommerce findBySiteAndUser(Site site, String user);
	
	public List<ClientEcommerce> findByUserAndPassword(String user, String password);

	public ClientEcommerce findBySiteAndUserAndPassword(Site site, String user, String password);

	@Query("select count(id) from ClientEcommerce where site = ?1 and creationDate >= ?2")
	public Long countBySiteAndCreationDateGreaterThan(Site site, Calendar dtInit);

}
