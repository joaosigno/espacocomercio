package net.danielfreire.products.ecommerce.model.repository;

import java.util.List;

import net.danielfreire.products.ecommerce.model.domain.ClientAdmin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientAdminRepository extends JpaRepository<ClientAdmin, Integer> {

	List<ClientAdmin> findByUserAndPassword(String user, String password);

}
