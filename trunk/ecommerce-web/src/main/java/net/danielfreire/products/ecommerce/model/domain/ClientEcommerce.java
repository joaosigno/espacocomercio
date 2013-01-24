package net.danielfreire.products.ecommerce.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	@Column(name="permission_type1")
	private Boolean permission_type1;
	@Column(name="permission_type2")
	private Boolean permission_type2;
	@Column(name="permission_type3")
	private Boolean permission_type3;
	@Column(name="permission_type4")
	private Boolean permission_type4;
	
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

	public Boolean getPermission_type1() {
		return permission_type1;
	}

	public void setPermission_type1(Boolean permission_type1) {
		this.permission_type1 = permission_type1;
	}

	public Boolean getPermission_type2() {
		return permission_type2;
	}

	public void setPermission_type2(Boolean permission_type2) {
		this.permission_type2 = permission_type2;
	}

	public Boolean getPermission_type3() {
		return permission_type3;
	}

	public void setPermission_type3(Boolean permission_type3) {
		this.permission_type3 = permission_type3;
	}

	public Boolean getPermission_type4() {
		return permission_type4;
	}

	public void setPermission_type4(Boolean permission_type4) {
		this.permission_type4 = permission_type4;
	}
}
