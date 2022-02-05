package com.interviews.johnlewis.products.model;

import java.util.List;

public class ReducedProducts {
	
	private List<Product> products;
	
	public ReducedProducts(List<Product> products) {
		
		this.products = products;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
}
