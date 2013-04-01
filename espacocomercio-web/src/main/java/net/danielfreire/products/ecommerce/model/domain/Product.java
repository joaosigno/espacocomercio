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
@Table(name="product")
public class Product extends AbstractPersistable<Integer> {

	private static final long serialVersionUID = -2709345858968454136L;
	
	@ManyToOne
	@JoinColumn(name="site_id", referencedColumnName="id")
	private Site site;
	@Column(name="name", length=45)
	private String name;
	@Column(name="introduction", length=255)
	private String introduction;
	@Column(name="images")
	private String images;
	@Column(name="description")
	private String description;
	@Column(name="quantity")
	private Double quantity;
	@Column(name="unityvalue")
	private Double unityvalue;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="datecreate")
	private Calendar datecreate;
	@Column(name="key_url")
	private String keyUrl;
	@Column(name="quantity_frete")
	private Double quantityFrete;
	
	public Product() {
		super();
	}
	
	public Product(final Integer idProduct) {
		super();
		super.id = idProduct;
	}
	
	public Product(final Integer idProduct, final Integer sid) {
		super();
		super.id = idProduct;
		this.site = new Site(sid);
	}
	
	public String getName() {
		return name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(final String introduction) {
		this.introduction = introduction;
	}
	public String getImages() {
		return images;
	}
	public void setImages(final String images) {
		this.images = images;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(final Double quantity) {
		this.quantity = quantity;
	}
	public Double getUnityvalue() {
		return unityvalue;
	}
	public void setUnityvalue(final Double unityvalue) {
		this.unityvalue = unityvalue;
	}

	public Calendar getDatecreate() {
		return datecreate;
	}

	public void setDatecreate(final Calendar datecreate) {
		this.datecreate = datecreate;
	}

	public String getKeyUrl() {
		return keyUrl;
	}

	public void setKeyUrl(final String keyUrl) {
		this.keyUrl = keyUrl;
	}

	public Double getQuantityFrete() {
		return quantityFrete;
	}

	public void setQuantityFrete(final Double quantityFrete) {
		this.quantityFrete = quantityFrete;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(final Site site) {
		this.site = site;
	}
	

}
