package net.danielfreire.products.advocacy.model.domain;

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
@Table(name="advocacy_client")
public class AdvocacyClient extends AbstractPersistable<Integer> {
	
	private static final long serialVersionUID = -3686356598582369606L;
	
	@Column(name="name", length=255)
	private String name;
	@Column(name="email", length=255)
	private String email;
	@Column(name="rg", length=100)
	private String rg;
	@Column(name="cpf", length=20)
	private String cpf;
	@Temporal(TemporalType.DATE)
	@Column(name="born")
	private Calendar born;
	@Column(name="phone", length=20)
	private String phone;
	@Column(name="cellphone", length=20)
	private String cellphone;
	@Column(name="address_street", length=100)
	private String addressStreet;
	@Column(name="address_city", length=100)
	private String addressCity;
	@Column(name="address_zipcode", length=20)
	private String addressZipcode;
	@Column(name="address_number", length=20)
	private String addressNumber;
	@Column(name="address_complement", length=100)
	private String addressComplement;
	@ManyToOne
	@JoinColumn(name="advocacy_office_id", referencedColumnName="id")
	private AdvocacyOffice advocacyOffice;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getAddressStreet() {
		return addressStreet;
	}
	public void setAddressStreet(String addressStreet) {
		this.addressStreet = addressStreet;
	}
	public String getAddressCity() {
		return addressCity;
	}
	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}
	public String getAddressZipcode() {
		return addressZipcode;
	}
	public void setAddressZipcode(String addressZipcode) {
		this.addressZipcode = addressZipcode;
	}
	public String getAddressNumber() {
		return addressNumber;
	}
	public void setAddressNumber(String addressNumber) {
		this.addressNumber = addressNumber;
	}
	public String getAddressComplement() {
		return addressComplement;
	}
	public void setAddressComplement(String addressComplement) {
		this.addressComplement = addressComplement;
	}
	public AdvocacyOffice getAdvocacyOffice() {
		return advocacyOffice;
	}
	public void setAdvocacyOffice(AdvocacyOffice advocacyOffice) {
		this.advocacyOffice = advocacyOffice;
	}
	public Calendar getBorn() {
		return born;
	}
	public void setBorn(Calendar born) {
		this.born = born;
	}

}
