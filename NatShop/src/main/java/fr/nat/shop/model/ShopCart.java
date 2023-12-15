package fr.nat.shop.model;

import java.util.Map;

public class ShopCart {

	Map<Integer, ShopCartLine> addedProducts;
	Double totalAmount;
	
	public ShopCart() {
	}
	
	public ShopCart(Map<Integer, ShopCartLine> addedProducts, Double totalAmount) {
		this.addedProducts = addedProducts;
		this.totalAmount = totalAmount;
	}

	public Map<Integer, ShopCartLine> getAddedProducts() {
		return addedProducts;
	}

	public void setAddedProducts(Map<Integer, ShopCartLine> addedProducts) {
		this.addedProducts = addedProducts;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
}
