package com.interviews.johnlewis.products.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.Module.SetupContext;
import com.interviews.johnlewis.products.model.ColorSwatch;
import com.interviews.johnlewis.products.model.Product;
import com.interviews.johnlewis.products.model.ReducedProducts;
import com.interviews.johnlewis.products.service.ProductService;

@WebMvcTest
@ExtendWith(SpringExtension.class)
public class ProductControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	ProductService productService;

	private static ReducedProducts products;
	
	@BeforeAll
	public static void setUp() {
		List<ColorSwatch> colorSwatches = Arrays.asList(new ColorSwatch("Blue","oxfb123","3553"));
		products = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatches,"£12","Was £12 now £10")));
	}
	
	@Test
	public void checkPriceReductionAPIis2XX() throws Exception {
		when(productService.getProductsWithReducedPrice("ShowWasNow")).thenReturn(products);
		mockMvc.perform(
				get("/api/price/reduction")
				.param("labelType", "")
				.contentType(MediaType.APPLICATION_JSON)
			).andExpect(status().isOk()
			).andExpect(jsonPath("$.products[0].productId", is("567"))
			).andExpect(jsonPath("$.products.length()", is(1))
			).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
		verify(productService).getProductsWithReducedPrice(anyString());
	}

}
