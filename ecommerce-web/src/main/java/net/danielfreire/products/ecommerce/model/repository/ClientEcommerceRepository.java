package net.danielfreire.products.ecommerce.model.repository;

import java.util.List;

import net.danielfreire.products.ecommerce.model.domain.ClientEcommerce;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientEcommerceRepository extends JpaRepository<ClientEcommerce, Integer> {
	
	public Page<ClientEcommerce> findBySiteId(Integer siteId, Pageable p);
	
	public List<ClientEcommerce> findBySiteId(Integer siteId);
	
	public ClientEcommerce findBySiteIdAndUser(Integer siteId, String user);
	
	public ClientEcommerce findBySiteIdAndUserAndPassword(Integer siteId, String user, String password);

}
