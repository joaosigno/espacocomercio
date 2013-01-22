package net.danielfreire.products.sso.model.domain;

import java.io.Serializable;


/**
 * The persistent class for the newsletter database table.
 * 
 */
public class Newsletter implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String description;
	private String title;
	//bi-directional many-to-one association to Site
	private Site site;

	public Newsletter() {
	}
	
	public Newsletter(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Site getSite() {
		return this.site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

}