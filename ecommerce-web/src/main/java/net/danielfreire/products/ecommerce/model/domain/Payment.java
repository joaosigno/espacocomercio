package net.danielfreire.products.ecommerce.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name="payment")
public class Payment extends AbstractPersistable<Integer> {

	private static final long serialVersionUID = 8796649328843829323L;

	@Column(name="site_id")
	private Integer siteId;
	@Column(name="name", length=255)
	private String name;
	@Column(name="description", length=255)
	private String description;
	@Column(name="url", length=255)
	private String url;
	
	public Payment() {
		super();
	}
	
	public Payment(Integer id) {
		super();
		setId(id);
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	

}
