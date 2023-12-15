package fr.nat.shop.controller;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import fr.nat.shop.model.Product;
import fr.nat.shop.model.ShopCart;
import fr.nat.shop.services.ShopService;

@Controller
public class ConsoleShopController implements ShopController {

	@Autowired
	ShopService shopService;

	@Override
	public void controller() {
		List<Product> getAllProducts = shopService.getProductsInventory();
		ShopCart shopCart = shopService.getShopCart();
		
		System.out.println("Welcome! Our shop contains " + getAllProducts.size() + " products.");
		System.out.println("Please fill quantity for each one. Quantity = 0 will ignore the product.");
		
		try (Scanner sc = new Scanner(System.in)) {
			for (Product product : getAllProducts) {
				System.out.println(product.toString() + " Quantity ? ");
				String userChoice = sc.nextLine();
				if (!shopService.addQuantityToProduct(shopCart, product.getId(), userChoice)) {
					System.out.println("Unknown quantity so ignored.");
				}
			}
		}
		
		System.out.println("Shopping finished.\n");
		System.out.println(shopService.displayFinalShopCart(shopCart));
		String fileName = shopService.generateFile(shopCart);
		System.out.print(fileName != null ? fileName + " generated .\n" : "");
		System.out.println("Thank you. See you soon");
	}
}
