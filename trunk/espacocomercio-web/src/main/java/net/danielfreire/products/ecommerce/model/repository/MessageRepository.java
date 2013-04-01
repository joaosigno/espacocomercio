package net.danielfreire.products.ecommerce.model.repository;

import java.util.Calendar;

import net.danielfreire.products.ecommerce.model.domain.Message;
import net.danielfreire.products.ecommerce.model.domain.Site;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query("select count(id) from Message where site = ?1 and creationDate >= ?2")
	Long countBySiteAndCreationDateGreaterThan(Site site, Calendar dtInit);
}
