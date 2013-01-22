package net.danielfreire.products.advocacy.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name="advocacy_user")
public class AdvocacyUser extends AbstractPersistable<Integer> {

	private static final long serialVersionUID = -4570973945190728897L;
	
	@Column(name="username", length=100)
	private String username;
	@Column(name="userpassword", length=10)
	private String userpassword;
	@Column(name="manage_office")
	private Boolean manageOffice;
	@Column(name="manage_user")
	private Boolean manageUser;
	@Column(name="manage_finance")
	private Boolean manageFinance;
	@Column(name="manage_client")
	private Boolean manageClient;
	@Column(name="view_client")
	private Boolean viewClient;
	@Column(name="manage_contract")
	private Boolean manageContract;
	@Column(name="view_contract")
	private Boolean viewContract;
	@Column(name="view_lawyer")
	private Boolean viewLawyer;
	@ManyToOne
	@JoinColumn(name="advocacy_office_id", referencedColumnName="id")
	private AdvocacyOffice advocacyOffice;
	@Column(name="manage_lawyer")
	private Boolean manageLawyer;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

	public Boolean getManageOffice() {
		return manageOffice;
	}

	public void setManageOffice(Boolean manageOffice) {
		this.manageOffice = manageOffice;
	}

	public Boolean getManageUser() {
		return manageUser;
	}

	public void setManageUser(Boolean manageUser) {
		this.manageUser = manageUser;
	}

	public Boolean getManageFinance() {
		return manageFinance;
	}

	public void setManageFinance(Boolean manageFinance) {
		this.manageFinance = manageFinance;
	}

	public Boolean getManageClient() {
		return manageClient;
	}

	public void setManageClient(Boolean manageClient) {
		this.manageClient = manageClient;
	}

	public Boolean getViewClient() {
		return viewClient;
	}

	public void setViewClient(Boolean viewClient) {
		this.viewClient = viewClient;
	}

	public Boolean getManageContract() {
		return manageContract;
	}

	public void setManageContract(Boolean manageContract) {
		this.manageContract = manageContract;
	}

	public Boolean getViewContract() {
		return viewContract;
	}

	public void setViewContract(Boolean viewContract) {
		this.viewContract = viewContract;
	}

	public Boolean getViewLawyer() {
		return viewLawyer;
	}

	public void setViewLawyer(Boolean viewLawyer) {
		this.viewLawyer = viewLawyer;
	}

	public AdvocacyOffice getAdvocacyOffice() {
		return advocacyOffice;
	}

	public void setAdvocacyOffice(AdvocacyOffice advocacyOffice) {
		this.advocacyOffice = advocacyOffice;
	}
	
	public void setId(Integer id) {
		super.setId(id);
	}

	public Boolean getManageLawyer() {
		return manageLawyer;
	}

	public void setManageLawyer(Boolean manageLawyer) {
		this.manageLawyer = manageLawyer;
	}
}
