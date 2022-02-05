package com.interviews.johnlewis.products.model;

import java.util.List;

public class Product {

	private String productId;
	private String title;
	private List<ColorSwatch> colorSwatches;
	private String nowPrice;
	private String priceLabel;

	public Product(String productId, String title, List<ColorSwatch> colorSwatches, String nowPrice, String priceLabel) {
		this.productId = productId;
		this.title = title;
		this.colorSwatches = colorSwatches;
		this.nowPrice = nowPrice;
		this.priceLabel = priceLabel;
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

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", title=" + title + ", colorSwatches=" + colorSwatches
				+ ", nowPrice=" + nowPrice + ", priceLabel=" + priceLabel + "]";
	}
	
	

}
