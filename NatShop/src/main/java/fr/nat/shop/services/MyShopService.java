package fr.nat.shop.services;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.nat.shop.model.Product;
import fr.nat.shop.model.ShopCart;
import fr.nat.shop.model.ShopCartLine;
import fr.nat.shop.repository.ShopRepository;

@Service
public class MyShopService implements ShopService {

	public static int PAD_MAX_LENGTH = 10;
	public static char PAD_CHAR_VOID = ' ';
	public static char PAD_CHAR_6 = '-';
	public static char PAD_CHAR_LN = '\n';
	public static String[] HEADER = { "Product", "Quantity", "Price", "Total" };
	public static String[] FOOTER = { "Total", "", "" };

	@Autowired
	ShopRepository shopRepo;

	@Override
	public void setShopRepository(ShopRepository shopRepo) {
		this.shopRepo = shopRepo;
	}
	
	@Override
	public ShopRepository getShopRepository() {
		return shopRepo;
	}

	@Override
	public List<Product> getProductsInventory() {
		return shopRepo.getProductsInventory();
	}
	
	@Override
	public ShopCart getShopCart() {
		return shopRepo.getShopCart();
	}

	@Override
	public boolean addQuantityToProduct(ShopCart shopCart, Integer productId, String userQuantity) {
		Integer quantity = null;
		try {
			quantity = Integer.valueOf(userQuantity);
		} catch (Exception e) {
			quantity = 0;
		}
		if (quantity == 0 || shopCart == null)
			return false;

		Product product = shopRepo.getProductById(productId);
		if (product == null)
			return false;
		
		shopRepo.addProductToShopCart(product, quantity);
		return true;
	}

	@Override
	public String displayFinalShopCart(ShopCart shopCart) {

		StringBuilder sb = new StringBuilder();
		sb.append(padLeft(HEADER[0]));
		sb.append(padRight(HEADER[1]));
		sb.append(padRight(HEADER[2]));
		sb.append(padRight(HEADER[3]));
//		for (String string : HEADER) {
//			sb.append(padLeft(string));
//		}
		
		sb.append(PAD_CHAR_LN).append(padAll(PAD_MAX_LENGTH * HEADER.length)).append(PAD_CHAR_LN);

		if (shopCart != null) {
			Map<Integer, ShopCartLine> addedProducts = shopCart.getAddedProducts();
			if (addedProducts != null) {
				for (Map.Entry<Integer, ShopCartLine> entry : addedProducts.entrySet()) {
					ShopCartLine shopCartLine = entry.getValue();

					Product product = shopCartLine.getProduct();
					sb.append(padLeft(product.getName()));
					sb.append(padRight(shopCartLine.getQuantity().toString()));
					sb.append(padRight(Util.formatDouble(product.getPrice())));
					sb.append(padRight(Util.formatDouble(shopCartLine.getAmount()))).append(PAD_CHAR_LN);
				}
			}
		}
		sb.append(padAll(PAD_MAX_LENGTH * HEADER.length)).append(PAD_CHAR_LN);

		for (String string : FOOTER) {
			sb.append(padLeft(string));
		}
		sb.append(padRight(Util.formatDouble(shopCart.getTotalAmount()))).append(PAD_CHAR_LN);

		return sb.toString();
	}
	
	private char[] pad(char padChar, int length) {
		char[] pad = new char[length];
		Arrays.fill(pad, padChar);
		return pad;
	}

	private StringBuilder padLeft(String str) {
		StringBuilder sb = new StringBuilder();
		return sb.append(str).append(pad(PAD_CHAR_VOID, PAD_MAX_LENGTH - str.length()));
	}

	private StringBuilder padAll(int length) {
		StringBuilder sb = new StringBuilder();
		return sb.append(pad(PAD_CHAR_6, length));
	}

	private StringBuilder padRight(String str) {
		StringBuilder sb = new StringBuilder();
		return sb.append(pad(PAD_CHAR_VOID, PAD_MAX_LENGTH - str.length())).append(str);
	}

	@Override
	public String generateFile(ShopCart shopCart) {
		
		String fileName = shopRepo.getOrderName();
		
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(fileName);
			writer.println(Util.createJSONObject(shopCart));
		} catch (FileNotFoundException e) {
			fileName = null;
		}
		finally {
			if (writer != null) writer.close();
		}
		return fileName;
	}

}
