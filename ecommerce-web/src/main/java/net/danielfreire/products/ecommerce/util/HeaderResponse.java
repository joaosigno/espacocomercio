package net.danielfreire.products.ecommerce.util;

import java.io.Serializable;
import java.util.ArrayList;

import net.danielfreire.products.ecommerce.model.domain.ProductCategory;

public class HeaderResponse implements Serializable {
	
	private static final long serialVersionUID = 8555345357419575885L;
	
	private ArrayList<ProductCategory> menu;
	private Integer sessionCart;
	private String sessionClient;
	
	public ArrayList<ProductCategory> getMenu() {
		return menu;
	}
	public void setMenu(ArrayList<ProductCategory> menu) {
		ArrayList<ProductCategory> menuNew = new ArrayList<ProductCategory>();
		for (ProductCategory pc : menu) {
			pc.setDescription("");
			//pc.setSiteId(null);
			menuNew.add(pc);
		}
		this.menu = menuNew;
	}
	public Integer getSessionCart() {
		return sessionCart;
	}
	public void setSessionCart(Integer sessionCart) {
		this.sessionCart = sessionCart;
	}
	public String getSessionClient() {
		return sessionClient;
	}
	public void setSessionClient(String sessionClient) {
		this.sessionClient = sessionClient;
	}

}
