package net.danielfreire.products.ecommerce.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name="frete_parameter")
public class FreteParameter extends AbstractPersistable<Integer> {

	private static final long serialVersionUID = 1548202239998245775L;
	
	@ManyToOne
	@JoinColumn(name="site_id", referencedColumnName="id")
	private Site site;
	@Column(name="state")
	private String state;
	@Column(name="value")
	private Double value;
	@Column(name="quantity_day")
	private Integer quantityDay;
	
	public Integer getQuantityDay() {
		return quantityDay;
	}

	public void setQuantityDay(Integer quantityDay) {
		this.quantityDay = quantityDay;
	}

	public FreteParameter() {
		super();
	}
	
	public FreteParameter(Integer id) {
		super();
		super.setId(id);
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

}
