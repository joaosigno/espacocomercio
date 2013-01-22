package net.danielfreire.products.sso.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;


/**
 * The persistent class for the client database table.
 * 
 */
@Entity
@Table(name="client")
public class Client extends AbstractPersistable<Integer> {

	private static final long serialVersionUID = -8988479105523136680L;
	
	@Column(name="usermail", length=255)
	private String user;
	@Column(name="userpassword", length=20)
	private String password;
	@ManyToOne
	@JoinColumn(name="site_id", referencedColumnName="id")
	private Site site;
	
	public Client() {
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

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
	

}