package net.danielfreire.products.ecommerce.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name="site")
public class Site extends AbstractPersistable<Integer> {

	private static final long serialVersionUID = -7943887864617331179L;
	
	@Column(name="name", length=45)
	private String name;
	@Column(name="description", length=255)
	private String description;
	@Column(name="facebook", length=255)
	private String facebook;
	
	public Site() {
		// TODO Auto-generated constructor stub
	}
	
	public Site(Integer id) {
		super.setId(id);
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

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}
	
	
}
