package net.danielfreire.products.advocacy.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name="advocacy_office")
public class AdvocacyOffice extends AbstractPersistable<Integer> {
	
	private static final long serialVersionUID = -3848619579886246954L;
	
	@Column(name="name", length=100)
	private String name;
	@Column(name="subtitle", length=150)
	private String subtitle;
	
	public AdvocacyOffice() {
		// TODO Auto-generated constructor stub
	}
	
	public AdvocacyOffice(Integer id) {
		super.setId(id);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

}
