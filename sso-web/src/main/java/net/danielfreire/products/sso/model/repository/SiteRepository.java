package net.danielfreire.products.sso.model.repository;

import net.danielfreire.products.sso.model.domain.Site;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteRepository extends JpaRepository<Site, Integer> {

}
