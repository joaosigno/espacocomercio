package net.danielfreire.products.ecommerce.model.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name="product_reserved")
public class ProductReserved extends AbstractPersistable<Integer> {

	private static final long serialVersionUID = -1914421837738952962L;
	
	@Column(name="product_id")
	private Integer productId;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="datereserved")
	private Calendar dateReserved;
	@Column(name="quantity")
	private Double quantity;
	@Column(name="active")
	private Boolean active;
	
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Calendar getDateReserved() {
		return dateReserved;
	}
	public void setDateReserved(Calendar dateReserved) {
		this.dateReserved = dateReserved;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	

}
