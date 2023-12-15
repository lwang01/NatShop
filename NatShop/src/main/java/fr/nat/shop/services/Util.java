package fr.nat.shop.services;

import java.text.DecimalFormat;
import java.util.Map;

import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;

import fr.nat.shop.model.Product;
import fr.nat.shop.model.ShopCart;
import fr.nat.shop.model.ShopCartLine;

public class Util {
	
	public static DecimalFormat f = new DecimalFormat("##.00");
	
	public static String formatDouble(Double value) {
		if (value == null) return "";
		String str = f.format(value);
		return str.replace(',', '.');
	}

	public static JSONObject createJSONObject(ShopCart shopCart) {
		
		JSONObject jo = new JSONObject();
		
		if (shopCart != null) {
			jo.put("TotalCartShop", formatDouble(shopCart.getTotalAmount()));
						
			Map<Integer, ShopCartLine> addedProducts = shopCart.getAddedProducts();
			if (addedProducts != null) {
				// To build the JSONArray, we use CDL
				StringBuilder sb = new StringBuilder();
				sb.append("Product, Quantity, Price, Total \n");
				
				for (Map.Entry<Integer, ShopCartLine> entry : addedProducts.entrySet()) {
					ShopCartLine shopCartLine = entry.getValue();

					Product product = shopCartLine.getProduct();
					sb.append(product.getName()).append(",");
					sb.append(shopCartLine.getQuantity()).append(",");
					sb.append(formatDouble(product.getPrice())).append(",");
					sb.append(formatDouble(shopCartLine.getAmount())).append("\n");
				}
				JSONArray ja = CDL.toJSONArray(sb.toString());
				jo.put("Products", ja);
			}
		}
		
		return jo;
	}
}
