package net.danielfreire.products.ecommerce.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name="product_category")
public class ProductCategory extends AbstractPersistable<Integer> {

	private static final long serialVersionUID = -1899243292932798398L;
	
	@ManyToOne
	@JoinColumn(name="site_id", referencedColumnName="id")
	private Site site;
	@Column(name="name", length=45)
	private String name;
	@Column(name="description", length=255)
	private String description;
	@Column(name="key_url", length=45)
	private String keyUrl;
	
	public ProductCategory() {
		super();
	}
	
	public ProductCategory(final Integer idCategory) {
		super();
		super.setId(idCategory);
	}
	
	public Site getSite() {
		return site;
	}

	public void setSite(final Site site) {
		this.site = site;
	}

	public String getName() {
		return name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	public String getKeyUrl() {
		return keyUrl;
	}

	public void setKeyUrl(final String keyUrl) {
		this.keyUrl = keyUrl;
	}

}
