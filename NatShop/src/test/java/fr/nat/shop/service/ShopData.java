package fr.nat.shop.service;

import java.util.HashMap;
import java.util.Map;

import fr.nat.shop.model.Product;
import fr.nat.shop.model.ShopCart;
import fr.nat.shop.model.ShopCartLine;

public class ShopData {

	// Simulating Database
	public static final Product PEN = new Product(1, "Pen", 1.50);
	public static final Product BOOK = new Product(2, "Book", 8.00);
	public static final Product GLUE = new Product(3, "Glue", 2.25);

	public static final ShopCart SHOP_CART = new ShopCart();
	
	static {
		Integer quantityPen = 3;
		ShopCartLine shopCartLinePen = new ShopCartLine();
		shopCartLinePen.setProduct(ShopData.PEN);
		shopCartLinePen.setQuantity(quantityPen);
		shopCartLinePen.setAmount(quantityPen * ShopData.PEN.getPrice());
		
		Integer quantityBook = 2;
		ShopCartLine shopCartLineBook = new ShopCartLine();
		shopCartLineBook.setProduct(ShopData.BOOK);
		shopCartLineBook.setQuantity(quantityBook);
		shopCartLineBook.setAmount(quantityBook * ShopData.BOOK.getPrice());
		
		Map<Integer, ShopCartLine> productsAdded = new HashMap<>();
		productsAdded.put(ShopData.PEN.getId(), shopCartLinePen);
		productsAdded.put(ShopData.BOOK.getId(), shopCartLineBook);
		SHOP_CART.setAddedProducts(productsAdded);
		SHOP_CART.setTotalAmount(shopCartLinePen.getAmount() + shopCartLineBook.getAmount());
	}
}
