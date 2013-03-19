package net.danielfreire.products.advocacy.model.repository;

import net.danielfreire.products.advocacy.model.domain.AdvocacyOffice;
import net.danielfreire.products.advocacy.model.domain.AdvocacyUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvocacyUserRepository extends JpaRepository<AdvocacyUser, Integer> {

	AdvocacyUser findByUsernameAndUserpassword(String username, String userpassword);
	
	Page<AdvocacyUser> findByAdvocacyOffice(AdvocacyOffice advocacyOffice, Pageable pageable);
	
	AdvocacyUser findByUsername(String username);

}
