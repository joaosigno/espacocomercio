package net.danielfreire.products.sso.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;


/**
 * The persistent class for the site database table.
 * 
 */
@Entity
@Table(name="site")
public class Site extends AbstractPersistable<Integer> {

	private static final long serialVersionUID = 9181785768587096848L;
	
	@Column(name="description", length=255)
	private String description;
	@Column(name="name", length=45)
	private String name;
	@Column(name="urladm", length=100)
	private String urladm;
	@Column(name="urlsite", length=100)
	private String urlsite;

	public Site() {
	}
	
	public Site(Integer id) {
		setId(id);
	}
	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrladm() {
		return this.urladm;
	}

	public void setUrladm(String urladm) {
		this.urladm = urladm;
	}

	public String getUrlsite() {
		return this.urlsite;
	}

	public void setUrlsite(String urlsite) {
		this.urlsite = urlsite;
	}
	
	public void setId(Integer id) {
		super.setId(id);
	}

}