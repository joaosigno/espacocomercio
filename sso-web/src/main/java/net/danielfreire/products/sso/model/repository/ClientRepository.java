package net.danielfreire.products.sso.model.repository;

import java.util.List;

import net.danielfreire.products.sso.model.domain.Client;
import net.danielfreire.products.sso.model.domain.Site;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
	
	public Client findByUserAndPassword(String user, String password);
	
	public Client findByUserAndSite(String user, Site site);
	
	public List<Client> findBySite(Site site);

}
