package net.danielfreire.products.advocacy.model.repository;

import net.danielfreire.products.advocacy.model.domain.AdvocacyClient;
import net.danielfreire.products.advocacy.model.domain.AdvocacyOffice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvocacyClientRepository extends JpaRepository<AdvocacyClient, Integer> {
	
	Page<AdvocacyClient> findByAdvocacyOffice(AdvocacyOffice advocacyOffice, Pageable pageable);

}
