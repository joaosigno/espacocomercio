package net.danielfreire.products.ecommerce.model.repository;

import java.util.List;

import net.danielfreire.products.ecommerce.model.domain.ClientAdmin;
import net.danielfreire.products.ecommerce.model.domain.Site;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientAdminRepository extends JpaRepository<ClientAdmin, Integer> {

	List<ClientAdmin> findByUserAndPassword(String user, String password);

	Page<ClientAdmin> findBySiteAndPermissionNot(Site site, Integer permission, Pageable p);

	Page<ClientAdmin> findBySite(Site site, Pageable p);

	ClientAdmin findBySiteAndUser(Site site, String user);

}
