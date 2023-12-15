package fr.nat.shop.services;

import java.util.List;

import fr.nat.shop.model.Product;
import fr.nat.shop.model.ShopCart;
import fr.nat.shop.repository.ShopRepository;

public interface ShopService {

	void setShopRepository(ShopRepository shopRepo);
	
	ShopRepository getShopRepository();
	
	List<Product> getProductsInventory();
	
	ShopCart getShopCart();
	
	boolean addQuantityToProduct(ShopCart shopCart, Integer productId, String userQuantity);
	
	String displayFinalShopCart(ShopCart shopCart);
	
	String generateFile(ShopCart shopCart);
}
