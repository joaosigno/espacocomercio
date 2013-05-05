package net.danielfreire.products.ecommerce.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name="client_admin")
public class ClientAdmin extends AbstractPersistable<Integer> {
	
	private static final long serialVersionUID = 7600797541522560253L;
	
	@ManyToOne
	@JoinColumn(name="site_id", referencedColumnName="id")
	private Site site;
	@Column(name="usermail", length=255)
	private String user;
	@Column(name="userpassword", length=10)
	private String password;
	@Column(name="permission_type")
	private Integer permission;
	
	public ClientAdmin(Integer id) {
		super.setId(id);
	}
	
	public ClientAdmin() {
		// TODO Auto-generated constructor stub
	}
	
	public Site getSite() {
		return site;
	}
	public void setSite(Site site) {
		this.site = site;
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
	public Integer getPermission() {
		return permission;
	}
	public void setPermission(Integer permission) {
		this.permission = permission;
	}
}
