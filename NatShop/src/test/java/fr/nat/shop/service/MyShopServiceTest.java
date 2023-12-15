package fr.nat.shop.service;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import fr.nat.shop.model.Product;
import fr.nat.shop.model.ShopCart;
import fr.nat.shop.repository.ShopRepository;
import fr.nat.shop.services.MyShopService;

class MyShopServiceTest {
	
	@Autowired
	private MyShopService srv;
	
	@Mock
	private ShopRepository repoMock;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		
		srv = new MyShopService();
		srv.setShopRepository(repoMock);
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void testSetShopRepository() {
		// When
		if (repoMock == null) {
			// Then
			Assertions.fail("Not expected");
		}
	}
	
	@Test
	void testGetShopRepository() {
		// Then
		Assertions.assertNotNull(srv.getShopRepository());
	}
	
	@Test
	void testGetProductsInventoryOK() {
		// Given data OK
		List<Product> products = List.of(ShopData.PEN, ShopData.BOOK, ShopData.GLUE);
		Mockito.when(repoMock.getProductsInventory()).thenReturn(products);
		
		// When
		List<Product> res = srv.getProductsInventory();
		
		// Then
		Assertions.assertNotNull(res);
	}	

	@Test
	void testGetShopCartOK() {
		// Given data OK
		Mockito.when(repoMock.getShopCart()).thenReturn(ShopData.SHOP_CART);
		
		// When
		ShopCart res = srv.getShopCart();
		
		// Then
		Assertions.assertNotNull(res);
	}

	@Test
	void testAddQuantityToProductOK() {
		// Given data OK
		String userQuantity = "20";
		Mockito.when(repoMock.getProductById(ShopData.PEN.getId())).thenReturn(ShopData.PEN);
		
		// When
		boolean res = srv.addQuantityToProduct(ShopData.SHOP_CART, ShopData.PEN.getId(), userQuantity);
		
		// Then
		Assertions.assertTrue(res);
	}
	
	@Test
	void testAddQuantityToProductQtyKO() {
		// Given wrong quantity
		String userQuantity = "Quantity20";
		
		// When
		boolean res = srv.addQuantityToProduct(ShopData.SHOP_CART, ShopData.PEN.getId(), userQuantity);
		
		// Then
		Assertions.assertFalse(res);
	}
	
	@Test
	void testAddQuantityToProductPdtKO() {
		// Given wrong productId
		String userQuantity = "20";
		Integer idProduct = 5;
		Mockito.when(repoMock.getProductById(idProduct)).thenReturn(null);
		
		// When
		boolean res = srv.addQuantityToProduct(ShopData.SHOP_CART, idProduct, userQuantity);
		
		// Then
		Assertions.assertFalse(res);
	}

	@Test
	void testDisplayFinalShopCartOK() {
		// Given data OK
		ShopCart shopCart = ShopData.SHOP_CART;
				
		// When
		String res = srv.displayFinalShopCart(shopCart);
				
		// Then
		Assertions.assertNotNull(res);
	}

	@Test
	void testGenerateFile() {
		// Given data OK
		String fileName = "NatShopTest.json";
		Mockito.when(repoMock.getOrderName()).thenReturn(fileName);
		
		// When
		String res = srv.generateFile(ShopData.SHOP_CART);
						
		// Then
		Assertions.assertNotNull(res);
		Assertions.assertEquals(fileName, res);
	}

}
