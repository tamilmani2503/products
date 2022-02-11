package com.interviews.johnlewis.products.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interviews.johnlewis.products.model.ReducedProducts;
import com.interviews.johnlewis.products.response.ProductDetails;
import com.interviews.johnlewis.products.response.ProductResponse;
import com.interviews.johnlewis.products.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {
	
	@Autowired
	ProductService productsService;
	
	@GetMapping("/price/reduction")
	public ResponseEntity<ProductResponse> fetchProductsWithReducedPrice(@RequestParam(name = "labelType", 
												required= false, 
												defaultValue="ShowWasNow") 
												String labelType) {
		ReducedProducts products = productsService.getProductsWithReducedPrice(labelType);
		ProductResponse response = responseConvertor(products);
		return new ResponseEntity<ProductResponse>(response, HttpStatus.OK);
	}

	private ProductResponse responseConvertor(ReducedProducts products) {
		ProductResponse response = new ProductResponse();
		List<ProductDetails> productDetails = new ArrayList<>();
		products.getProducts().forEach(p -> {
			ProductDetails details = new ProductDetails();
			details.setNowPrice(p.getNowPrice());
			details.setProductId(p.getProductId());
			details.setTitle(p.getTitle());
			details.setPriceLabel(p.getPriceLabel());
			details.setColorSwatches(p.getColorSwatches());
			productDetails.add(details);
		});
		response.setProducts(productDetails);
		return response;
	}

}