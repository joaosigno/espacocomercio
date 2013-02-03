package net.danielfreire.products.advocacy.model.repository;

import net.danielfreire.products.advocacy.model.domain.AdvocacyClient;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvocacyClientRepository extends JpaRepository<AdvocacyClient, Integer> {

}
