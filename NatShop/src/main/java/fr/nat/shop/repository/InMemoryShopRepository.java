package fr.nat.shop.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import fr.nat.shop.model.Product;
import fr.nat.shop.model.ShopCart;
import fr.nat.shop.model.ShopCartLine;

@Repository
public class InMemoryShopRepository implements ShopRepository {

	// Simulating Database
	public static final Product PEN = new Product(1, "Pen", 1.50);
	public static final Product BOOK = new Product(2, "Book", 8.00);
	public static final Product GLUE = new Product(3, "Glue", 2.25);

	static List<Product> allProducts = List.of(PEN, BOOK, GLUE);
	static ShopCart shopCart = new ShopCart();

	@Override
	public List<Product> getProductsInventory() {
		return allProducts;
	}
	
	@Override
	public Product getProductById(Integer productId) {
		Product product;
		switch (productId) {
		case 1 -> product = PEN;
		case 2 -> product = BOOK;
		case 3 -> product = GLUE;
		default -> product = null;
		}
		return product;
	}

	@Override
	public void addProductToShopCart(Product product, Integer quantity) {

		Map<Integer, ShopCartLine> addedProducts = getShopCart().getAddedProducts();

		if (addedProducts.containsKey(product.getId())) {
			// This product is already in the shop cart (but never happens in our example)
			ShopCartLine shopCartLine = addedProducts.get(product.getId());
			Integer addQty = shopCartLine.getQuantity() + quantity;
			shopCartLine.setQuantity(addQty);
			shopCartLine.setAmount(product.getPrice() * addQty);
		} else {
			ShopCartLine shopCartLine = new ShopCartLine();
			shopCartLine.setProduct(product);
			shopCartLine.setQuantity(quantity);
			shopCartLine.setAmount(product.getPrice() * quantity);
			addedProducts.put(product.getId(), shopCartLine);
		}
		
		// Compute the total amount of the shop cart
		Double totalAmount = 0.0;
		for (Map.Entry<Integer, ShopCartLine> entry : addedProducts.entrySet()) {
			ShopCartLine shopCartLine = entry.getValue();
			totalAmount += shopCartLine.getAmount();
		}
		getShopCart().setTotalAmount(totalAmount);
	}

	@Override
	public ShopCart getShopCart() {
		if (shopCart.getAddedProducts() == null) {
			shopCart.setAddedProducts(new HashMap<Integer, ShopCartLine>());
		}
		return shopCart;
	}

	@Override
	public String getOrderName() {
		// Here will return the name for the generated file
		return "NatShop.json";
	}
}
