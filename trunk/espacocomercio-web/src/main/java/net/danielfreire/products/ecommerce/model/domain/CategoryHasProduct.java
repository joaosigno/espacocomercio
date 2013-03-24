package net.danielfreire.products.ecommerce.model.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name="category_has_product")
public class CategoryHasProduct extends AbstractPersistable<Integer> {

	private static final long serialVersionUID = -4941105629136196505L;
	
	@ManyToOne
	@JoinColumn(name="product_category_id", referencedColumnName="id")
	private ProductCategory category;
	@ManyToOne
	@JoinColumn(name="product_id", referencedColumnName="id")
	private Product product;
	
	public ProductCategory getCategory() {
		return category;
	}
	public void setCategory(ProductCategory category) {
		this.category = category;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

}
