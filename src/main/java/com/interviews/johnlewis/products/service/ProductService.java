package com.interviews.johnlewis.products.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.interviews.johnlewis.products.model.ColorSwatch;
import com.interviews.johnlewis.products.model.Product;
import com.interviews.johnlewis.products.model.ReducedProducts;
@Service
public class ProductService {

	public ReducedProducts getProductsWithReducedPrice(String labelType) {
		List<ColorSwatch> colorSwatches = Arrays.asList(new ColorSwatch("Blue","oxfb123","3553"));
		ReducedProducts products = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatches,"£12","Was £12 now £10")));
		return products;
	}

}
