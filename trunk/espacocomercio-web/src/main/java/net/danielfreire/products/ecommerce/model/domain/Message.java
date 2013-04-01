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
@Table(name="message")
public class Message extends AbstractPersistable<Integer> {
	
	private static final long serialVersionUID = -536952151357018201L;
	
	@ManyToOne
	@JoinColumn(name="site_id", referencedColumnName="id")
	private Site site;
	@Column(name="origin", length=100)
	private String origin;
	@Column(name="message", length=1000)
	private String messageText;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="creationDate")
	private Calendar creationDate;
	
	/**
	 * @return the site
	 */
	public Site getSite() {
		return site;
	}
	/**
	 * @param site the site to set
	 */
	public void setSite(final Site site) {
		this.site = site;
	}
	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}
	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(final String origin) {
		this.origin = origin;
	}
	/**
	 * @return the message
	 */
	public String getMessageText() {
		return messageText;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessageText(final String message) {
		this.messageText = message;
	}
	/**
	 * @return the creationDate
	 */
	public Calendar getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(final Calendar creationDate) {
		this.creationDate = creationDate;
	}

}
