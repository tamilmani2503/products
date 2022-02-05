package com.interviews.johnlewis.products.model;

public class ColorSwatch {

	private String color;
	private String rgbColor;
	private String skuid;

	public ColorSwatch(String color, String rgbColor, String skuid) {
		this.color = color;
		this.rgbColor = rgbColor;
		this.skuid = skuid;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getRgbColor() {
		return rgbColor;
	}

	public void setRgbColor(String rgbColor) {
		this.rgbColor = rgbColor;
	}

	public String getSkuid() {
		return skuid;
	}

	public void setSkuid(String skuid) {
		this.skuid = skuid;
	}

	@Override
	public String toString() {
		return "ColorSwatch [color=" + color + ", rgbColor=" + rgbColor + ", skuid=" + skuid + "]";
	}

	
}
