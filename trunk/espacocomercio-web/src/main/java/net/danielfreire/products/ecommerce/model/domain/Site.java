package net.danielfreire.products.ecommerce.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name="site")
public class Site extends AbstractPersistable<Integer> {

	private static final long serialVersionUID = -7943887864617331179L;
	
	@Column(name="name", length=45)
	private String name;
	@Column(name="description", length=2000)
	private String description;
	@Column(name="facebook", length=255)
	private String facebook;
	@Column(name="logo", length=255)
	private String logo;
	@Column(name="email", length=255)
	private String email;
	@Column(name="gmailPass", length=45)
	private String gmailPass;
	
	public Site() {
		super();
	}
	
	public Site(final Integer idSite) {
		super();
		super.setId(idSite);
	}
	
	public String getName() {
		return name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(final String facebook) {
		this.facebook = facebook;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(final String logo) {
		this.logo = logo;
	}
	
	public void setIdSite(final Integer idSite) {
		super.setId(idSite);
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * @return the gmailPass
	 */
	public String getGmailPass() {
		return gmailPass;
	}

	/**
	 * @param gmailPass the gmailPass to set
	 */
	public void setGmailPass(final String gmailPass) {
		this.gmailPass = gmailPass;
	}
	
}
