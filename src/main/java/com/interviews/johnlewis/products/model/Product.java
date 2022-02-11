package com.interviews.johnlewis.products.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class Product {
	
	@JsonAlias("productId")
	private String productId;
	@JsonAlias("title")
	private String title;
	@JsonAlias("colorSwatches")
	private List<ColorSwatch> colorSwatches;
	@JsonAlias("price")
	private Price price;
	private String nowPrice;
	private String priceLabel;
	private Double priceReduction;



	public Product() {
		
	}
	
	public Product(String productId, String title, List<ColorSwatch> colorSwatches, String nowPrice, String priceLabel, Price price) {
		this.productId = productId;
		this.title = title;
		this.colorSwatches = colorSwatches;
		this.nowPrice = nowPrice;
		this.priceLabel = priceLabel;
		this.price = price;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<ColorSwatch> getColorSwatches() {
		return colorSwatches;
	}

	public void setColorSwatches(List<ColorSwatch> colorSwatches) {
		this.colorSwatches = colorSwatches;
	}

	public String getNowPrice() {
		return nowPrice;
	}

	public void setNowPrice(String nowPrice) {
		this.nowPrice = nowPrice;
	}

	public String getPriceLabel() {
		return priceLabel;
	}

	public void setPriceLabel(String priceLabel) {
		this.priceLabel = priceLabel;
	}
	
	

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public Double getPriceReduction() {
		return priceReduction;
	}

	public void setPriceReduction(Double priceReduction) {
		this.priceReduction =priceReduction;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", title=" + title + ", colorSwatches=" + colorSwatches + ", price="
				+ price + ", nowPrice=" + nowPrice + ", priceLabel=" + priceLabel + ", priceReduction=" + priceReduction
				+ ", getProductId()=" + getProductId() + ", getTitle()=" + getTitle() + ", getColorSwatches()="
				+ getColorSwatches() + ", getNowPrice()=" + getNowPrice() + ", getPriceLabel()=" + getPriceLabel()
				+ ", getPrice()=" + getPrice() + ", getPriceReduction()=" + getPriceReduction() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	

}
