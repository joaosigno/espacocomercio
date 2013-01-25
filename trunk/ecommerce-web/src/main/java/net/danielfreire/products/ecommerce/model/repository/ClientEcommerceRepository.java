package net.danielfreire.products.ecommerce.model.repository;

import java.util.List;

import net.danielfreire.products.ecommerce.model.domain.ClientEcommerce;
import net.danielfreire.products.ecommerce.model.domain.Site;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientEcommerceRepository extends JpaRepository<ClientEcommerce, Integer> {
	
	public Page<ClientEcommerce> findBySite(Site site, Pageable p);
	
	public List<ClientEcommerce> findBySite(Site site);
	
	public ClientEcommerce findBySiteAndUser(Site site, String user);
	
	public List<ClientEcommerce> findByUserAndPassword(String user, String password);

}
