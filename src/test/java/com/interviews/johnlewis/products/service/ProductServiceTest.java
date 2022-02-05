package com.interviews.johnlewis.products.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.interviews.johnlewis.products.model.ColorSwatch;
import com.interviews.johnlewis.products.model.Product;
import com.interviews.johnlewis.products.model.ReducedProducts;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class ProductServiceTest {
	
	@Configuration
	public static class Config{
		@Bean
		public ProductService productService() {
			return new ProductService();
		}
	}
	
	@Autowired
	ProductService productService;
	
	private  static ReducedProducts products;
	
	@BeforeAll
	public static void setUp() {
		List<ColorSwatch> colorSwatches = Arrays.asList(new ColorSwatch("Blue","oxfb123","3553"));
		products = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatches,"£12","Was £12 now £10")));

	}
	
	@Test
	public void checkDefaultProductList() throws Exception {
		ReducedProducts actualProducts = productService.getProductsWithReducedPrice("ShowWasNow");
		assertEquals(products.getProducts().size(), actualProducts.getProducts().size());
	}
	
	
}
