package net.danielfreire.products.ecommerce.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name="product_has_order")
public class ProductHasOrder extends AbstractPersistable<Integer> {

	private static final long serialVersionUID = 8626745393376237120L;
	
	@ManyToOne
	@JoinColumn(name="product_id", referencedColumnName="id")
	private Product product;
	@Column(name="order_id")
	private Integer orderId;
	@Column(name="quantity")
	private Integer quantity;
	@Column(name="unityvalue")
	private Double unityvalue;
	
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getUnityvalue() {
		return unityvalue;
	}
	public void setUnityvalue(Double unityvalue) {
		this.unityvalue = unityvalue;
	}

}
