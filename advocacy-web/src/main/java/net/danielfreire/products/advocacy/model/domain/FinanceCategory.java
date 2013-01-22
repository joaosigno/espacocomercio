package net.danielfreire.products.advocacy.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name="finance_category")
public class FinanceCategory extends AbstractPersistable<Integer> {

	private static final long serialVersionUID = 1714367812560765941L;
	
	@Column(name="title", length=45)
	private String title;
	@ManyToOne
	@JoinColumn(name="advocacy_office_id", referencedColumnName="id")
	private AdvocacyOffice advocacyOffice;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public AdvocacyOffice getAdvocacyOffice() {
		return advocacyOffice;
	}
	public void setAdvocacyOffice(AdvocacyOffice advocacyOffice) {
		this.advocacyOffice = advocacyOffice;
	}
}
