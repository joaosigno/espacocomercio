package net.danielfreire.products.ecommerce.model.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="client_ecommerce")
public class ClientEcommerce extends AbstractPersistable<Integer> {

	private static final long serialVersionUID = -7283021108945100297L;
	
	@ManyToOne
	@JoinColumn(name="site_id", referencedColumnName="id")
	private Site site;
	@Column(name="active")
	private Boolean active;
	@Column(name="name", length=100)
	private String name;
	@Column(name="newsletter")
	private Boolean newsletter;
	@Column(name="usermail", length=255)
	private String user;
	@Column(name="userpassword", length=20)
	private String password;
	@Column(name="address_street", length=255)
	private String addressStreet;
	@Column(name="address_number", length=45)
	private String addressNumber;
	@Column(name="address_zipcode", length=10)
	private String addressZipcode;
	@Column(name="address_city", length=100)
	private String addressCity;
	@Column(name="address_complement", length=255)
	private String addressComplement;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="creationDate")
	private Calendar creationDate;
	@Column(name="phone1", length=15)
	private String phone1;
	@Column(name="phone2", length=15)
	private String phone2;
	
	public ClientEcommerce() {
		super();
	}
	
	public ClientEcommerce(final Integer idClient) {
		super();
		super.id = idClient;
	}
	
	public String getAddressStreet() {
		return addressStreet;
	}
	public void setAddressStreet(final String addressStreet) {
		this.addressStreet = addressStreet;
	}
	public String getAddressNumber() {
		return addressNumber;
	}
	public void setAddressNumber(final String addressNumber) {
		this.addressNumber = addressNumber;
	}
	public String getAddressZipcode() {
		return addressZipcode;
	}
	public void setAddressZipcode(final String addressZipcode) {
		this.addressZipcode = addressZipcode;
	}
	public String getAddressCity() {
		return addressCity;
	}
	public void setAddressCity(final String addressCity) {
		this.addressCity = addressCity;
	}
	public String getAddressComplement() {
		return addressComplement;
	}
	public void setAddressComplement(final String addressComplement) {
		this.addressComplement = addressComplement;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(final Boolean active) {
		this.active = active;
	}
	public String getName() {
		return name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	public Boolean getNewsletter() {
		return newsletter;
	}
	public void setNewsletter(final Boolean newsletter) {
		this.newsletter = newsletter;
	}
	public String getUser() {
		return user;
	}
	public void setUser(final String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(final String password) {
		this.password = password;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(final Site site) {
		this.site = site;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(final Calendar creationDate) {
		this.creationDate = creationDate;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(final String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(final String phone2) {
		this.phone2 = phone2;
	}

}
