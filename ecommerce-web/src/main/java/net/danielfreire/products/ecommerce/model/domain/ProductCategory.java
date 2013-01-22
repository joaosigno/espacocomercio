package net.danielfreire.products.ecommerce.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name="product_category")
public class ProductCategory extends AbstractPersistable<Integer> {

	private static final long serialVersionUID = -1899243292932798398L;
	
	@Column(name="site_id")
	private Integer siteId;
	@Column(name="name", length=45)
	private String name;
	@Column(name="description", length=255)
	private String description;
	@Column(name="key_url", length=45)
	private String keyUrl;
	
	public ProductCategory() {
		super();
	}
	
	public ProductCategory(Integer id) {
		super();
		super.setId(id);
	}
	
	public Integer getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getKeyUrl() {
		return keyUrl;
	}

	public void setKeyUrl(String keyUrl) {
		this.keyUrl = keyUrl;
	}

}
