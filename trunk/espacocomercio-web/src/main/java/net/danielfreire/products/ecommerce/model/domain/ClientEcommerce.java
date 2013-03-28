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
	
	public ClientEcommerce() {
		super();
	}
	
	public ClientEcommerce(Integer id) {
		super();
		setId(id);
	}
	
	public String getAddressStreet() {
		return addressStreet;
	}
	public void setAddressStreet(String addressStreet) {
		this.addressStreet = addressStreet;
	}
	public String getAddressNumber() {
		return addressNumber;
	}
	public void setAddressNumber(String addressNumber) {
		this.addressNumber = addressNumber;
	}
	public String getAddressZipcode() {
		return addressZipcode;
	}
	public void setAddressZipcode(String addressZipcode) {
		this.addressZipcode = addressZipcode;
	}
	public String getAddressCity() {
		return addressCity;
	}
	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}
	public String getAddressComplement() {
		return addressComplement;
	}
	public void setAddressComplement(String addressComplement) {
		this.addressComplement = addressComplement;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getNewsletter() {
		return newsletter;
	}
	public void setNewsletter(Boolean newsletter) {
		this.newsletter = newsletter;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setId(Integer id) {
		super.setId(id);
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

}
