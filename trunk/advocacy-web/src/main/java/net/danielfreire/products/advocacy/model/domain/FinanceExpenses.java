package net.danielfreire.products.advocacy.model.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name="finance_expenses")
public class FinanceExpenses extends AbstractPersistable<Integer> {

	private static final long serialVersionUID = -8372877469558940869L;
	
	@Column(name="title", length=200)
	private String title;
	@Temporal(TemporalType.DATE)
	@Column(name="date_expiration")
	private Calendar dateExpiration;
	@Temporal(TemporalType.DATE)
	@Column(name="date_payment")
	private Calendar datePayment;
	@Column(name="description", length=1000)
	private String description;
	@Column(name="advocacy_office_id")
	private Integer advocacyOffice;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Calendar getDateExpiration() {
		return dateExpiration;
	}
	public void setDateExpiration(Calendar dateExpiration) {
		this.dateExpiration = dateExpiration;
	}
	public Calendar getDatePayment() {
		return datePayment;
	}
	public void setDatePayment(Calendar datePayment) {
		this.datePayment = datePayment;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getAdvocacyOffice() {
		return advocacyOffice;
	}
	public void setAdvocacyOffice(Integer advocacyOffice) {
		this.advocacyOffice = advocacyOffice;
	}
}
