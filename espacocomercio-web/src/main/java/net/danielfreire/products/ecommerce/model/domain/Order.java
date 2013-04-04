package net.danielfreire.products.ecommerce.model.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name="order_client")
public class Order extends AbstractPersistable<Integer> {

	private static final long serialVersionUID = -8224950030554252123L;
	
	@ManyToOne
	@JoinColumn(name="client_id", referencedColumnName="id")
	private ClientEcommerce client;
	@ManyToOne
	@JoinColumn(name="payment_id", referencedColumnName="id")
	private Payment payment;
	@Column(name="totalvalue")
	private Double totalValue;
	@Column(name="sendcust")
	private Double sendCust;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="datecreate")
	private Calendar dateCreate;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="datepayment")
	private Calendar datePayment;
	@Column(name="status_order")
	private Integer statusOrder;
	@Column(name="discount")
	private Double discount;
	
	public Double getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(final Double totalValue) {
		this.totalValue = totalValue;
	}
	public Double getSendCust() {
		return sendCust;
	}
	public void setSendCust(final Double sendCust) {
		this.sendCust = sendCust;
	}
	public Calendar getDateCreate() {
		return dateCreate;
	}
	public void setDateCreate(final Calendar dateCreate) {
		this.dateCreate = dateCreate;
	}
	public Calendar getDatePayment() {
		return datePayment;
	}
	public void setDatePayment(final Calendar datePayment) {
		this.datePayment = datePayment;
	}
	public ClientEcommerce getClient() {
		return client;
	}
	public void setClient(final ClientEcommerce client) {
		this.client = client;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(final Payment payment) {
		this.payment = payment;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(final Double discount) {
		this.discount = discount;
	}
	public Integer getStatusOrder() {
		return statusOrder;
	}
	public void setStatusOrder(final Integer statusOrder) {
		this.statusOrder = statusOrder;
	}
	

}
