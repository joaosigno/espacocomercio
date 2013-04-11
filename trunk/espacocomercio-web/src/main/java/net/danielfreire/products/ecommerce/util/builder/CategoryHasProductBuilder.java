package net.danielfreire.products.ecommerce.util.builder;

import net.danielfreire.products.ecommerce.model.domain.CategoryHasProduct;
import net.danielfreire.products.ecommerce.model.domain.Product;
import net.danielfreire.products.ecommerce.model.domain.ProductCategory;

public class CategoryHasProductBuilder {
	
	private transient ProductCategory category;
	private transient Product product;
	
	public CategoryHasProductBuilder withCategory(final ProductCategory category) {
		this.category = category;
		
		return this;
	}

	public CategoryHasProductBuilder withProduct(final Product product) {
		this.product = product;
		
		return this;
	}
	
	public CategoryHasProduct build() {
		final CategoryHasProduct chp = new CategoryHasProduct();
		chp.setCategory(category);
		chp.setProduct(product);
		
		return chp;
	}
}
