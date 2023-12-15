package fr.nat.shop.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import fr.nat.shop.services.Util;

class UtilTest {

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testFormatDoubleNull() {
		Assertions.assertEquals("", Util.formatDouble(null));
	}
	
	@Test
	void testFormatDoubleOK() {
		Assertions.assertEquals("8.50", Util.formatDouble(8.50));
	}

	@Test
	void testCreateJSONObject() {
		Assertions.assertNotNull(Util.createJSONObject(ShopData.SHOP_CART));
	}

}
