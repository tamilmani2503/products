package com.interviews.johnlewis.products.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties
public class ReducedProducts {
	
	@JsonAlias("products")
	private List<Product> products;
	
	public ReducedProducts() {
		
	}

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
