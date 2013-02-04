package net.danielfreire.products.advocacy.model.repository;

import net.danielfreire.products.advocacy.model.domain.AdvocacyLawyer;
import net.danielfreire.products.advocacy.model.domain.AdvocacyOffice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvocacyLawyerRepository extends JpaRepository<AdvocacyLawyer, Integer> {
	
	Page<AdvocacyLawyer> findByAdvocacyOffice(AdvocacyOffice advocacyOffice, Pageable pageable);

}
