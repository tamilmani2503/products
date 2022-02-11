package com.interviews.johnlewis.products.response;

import java.util.List;

public class ProductResponse {
	
	List<ProductDetails> products;

	public ProductResponse(List<ProductDetails> product) {
		this.products = product;
	}

	public ProductResponse() {

	}

	public List<ProductDetails> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDetails> product) {
		this.products = product;
	}


}
