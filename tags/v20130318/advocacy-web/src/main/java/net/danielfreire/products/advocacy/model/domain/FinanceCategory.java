package net.danielfreire.products.advocacy.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name="finance_category")
public class FinanceCategory extends AbstractPersistable<Integer> {

	private static final long serialVersionUID = 1714367812560765941L;
	
	@Column(name="title", length=45)
	private String title;
	@Column(name="advocacy_office_id")
	private Integer advocacyOffice;
	
	public FinanceCategory() {
		// TODO Auto-generated constructor stub
	}
	
	public FinanceCategory(Integer id) {
		super.setId(id);
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getAdvocacyOffice() {
		return advocacyOffice;
	}
	public void setAdvocacyOffice(Integer advocacyOffice) {
		this.advocacyOffice = advocacyOffice;
	}
	
	public void setId(Integer id) {
		super.setId(id);
	}
}