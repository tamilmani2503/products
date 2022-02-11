package com.interviews.johnlewis.products.model;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class Price {
	
	@JsonAlias("was")
	private Object was;
	@JsonAlias("then1")
	private Object then1;
	@JsonAlias("then2")
	private Object then2;
	@JsonAlias("now")
	private Object now;
	
	
	public Price() {
		
	}
	
	
	public Price(Object was, Object then1, Object then2, Object now) {
		
		this.was = was;
		this.then1 = then1;
		this.then2 = then2;
		this.now = now;
	}


	public Object getWas() {
		return was;
	}
	public void setWas(Object was) {
		if (was instanceof String) {
			this.was = was;
		} else {
			this.was = (LinkedHashMap<String, String>) was;
		}
	}
	public Object getThen1() {
		return then1;
	}
	public void setThen1(Object then1) {
		if (then1 instanceof String) {
			this.then1 = then1;
		} else {
			this.then1 = (LinkedHashMap<String, String>) then1;
		}
	}
	public Object getThen2() {
		return then2;
	}
	public void setThen2(Object then2) {
		if (then2 instanceof String) {
			this.then2 = then2;
		} else {
			this.then2 = (LinkedHashMap<String, String>) then2;
		}
	}
	public Object getNow() {
		return now;
	}
	public void setNow(Object now) {
		if (now instanceof String) {
			this.now = now;
		} else {
			this.now = (LinkedHashMap<String, String>) now;
		}
	}

}
