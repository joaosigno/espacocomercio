package net.danielfreire.products.ecommerce.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name="frete_parameter")
public class FreteParameter extends AbstractPersistable<Integer> {

	private static final long serialVersionUID = 1548202239998245775L;
	
	@Column(name="site_id")
	private Integer siteId;
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
	
	public Integer getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
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

}
