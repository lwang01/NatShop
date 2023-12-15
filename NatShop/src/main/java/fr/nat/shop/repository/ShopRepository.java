package fr.nat.shop.repository;

import java.util.List;

import fr.nat.shop.model.Product;
import fr.nat.shop.model.ShopCart;

public interface ShopRepository {
	
	List<Product> getProductsInventory();
	
	Product getProductById(Integer productId);
	
	void addProductToShopCart(Product product, Integer quantity);
	
	ShopCart getShopCart(); 
	
	String getOrderName();
}
