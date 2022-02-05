package com.interviews.johnlewis.products.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interviews.johnlewis.products.model.ColorSwatch;
import com.interviews.johnlewis.products.model.Product;
import com.interviews.johnlewis.products.model.ReducedProducts;
import com.interviews.johnlewis.products.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {
	
	@Autowired
	ProductService productsService;
	
	@GetMapping("/price/reduction")
	public ResponseEntity<ReducedProducts> fetchProductsWithReducedPrice(@RequestParam(name = "labelType", 
												required= false, 
												defaultValue="ShowWasNow") 
												String labelType) {
		
		/*List<ColorSwatch> colorSwatches = Arrays.asList(new ColorSwatch("Blue","oxfb123","3553"));
		ReducedProducts products = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatches,"£12","Was £12 now £10")));*/
		return new ResponseEntity<ReducedProducts>(productsService.getProductsWithReducedPrice(labelType), HttpStatus.OK);
	}

}