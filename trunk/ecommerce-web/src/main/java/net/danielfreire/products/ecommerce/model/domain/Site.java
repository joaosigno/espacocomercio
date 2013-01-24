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
	private Integer name;
	@Column(name="description", length=255)
	private Integer description;
	
	public Integer getName() {
		return name;
	}
	public void setName(Integer name) {
		this.name = name;
	}
	public Integer getDescription() {
		return description;
	}
	public void setDescription(Integer description) {
		this.description = description;
	}
	
}
