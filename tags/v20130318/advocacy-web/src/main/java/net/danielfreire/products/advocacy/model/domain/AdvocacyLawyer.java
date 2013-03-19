package net.danielfreire.products.advocacy.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name="advocacy_lawyer")
public class AdvocacyLawyer extends AbstractPersistable<Integer> {
	
	private static final long serialVersionUID = 1587497428558088772L;
	
	@Column(name="name", length=255)
	private String name;
	@Column(name="email", length=255)
	private String email;
	@Column(name="cpf", length=20)
	private String cpf;
	@Column(name="cellphone", length=20)
	private String cellphone;
	@Column(name="naturalidade", length=100)
	private String naturalidade;
	@Column(name="estadocivil", length=20)
	private String estadocivil;
	@Column(name="profissao", length=100)
	private String profissao;
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
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getNaturalidade() {
		return naturalidade;
	}
	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}
	public String getEstadocivil() {
		return estadocivil;
	}
	public void setEstadocivil(String estadocivil) {
		this.estadocivil = estadocivil;
	}
	public String getProfissao() {
		return profissao;
	}
	public void setProfissao(String profissao) {
		this.profissao = profissao;
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
	
}
