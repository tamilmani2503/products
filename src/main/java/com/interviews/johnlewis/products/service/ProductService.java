package com.interviews.johnlewis.products.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.interviews.johnlewis.products.model.Price;
import com.interviews.johnlewis.products.model.Product;
import com.interviews.johnlewis.products.model.ReducedProducts;

@Component
public class ProductService {
	
	@Autowired
	RestTemplate restTemplate;
	
	private Map<String, String> assignRgbColor(Map<String, String> rgbColorMap) {
		rgbColorMap.put("Red", "FF0000");
		rgbColorMap.put("Pink", "FFC0CB");
		rgbColorMap.put("White", "FFFFFF");
		rgbColorMap.put("Blue", "0000FF");
		rgbColorMap.put("Purple", "800080");
		rgbColorMap.put("Yellow", "FFFF00");
		rgbColorMap.put("Multi", "xxxxxx");
		rgbColorMap.put("Black", "000000");
		rgbColorMap.put("Orange", "FFA500");
		rgbColorMap.put("Grey", "808080");
		rgbColorMap.put("Green", "008000");
		rgbColorMap.put("Neutrals","yyyyyy");
		return rgbColorMap;
	}
	
	private static final String apiUrl = "https://api.johnlewis.com/"
			+ "search/api/rest/v2/catalog/products/search/keyword?"
			+ "q=dresses&key=AIzaSyDD_6O5gUgC4tRW5f9kxC0_76XRC8W7_mI";
	
	Predicate<Double> priceValue =  s -> s < 10.00;
	
	Function<Double, String> priceConversion = (nowPrice) -> {
												return priceValue.test(nowPrice) ? "£".concat(String.valueOf(nowPrice))
														:"£".concat(splitPriceValue(nowPrice));
											};
											
	
											
	private String splitPriceValue(Double nowPrice) {
		String nowPriceString = String.valueOf(nowPrice);
		String[] splitStr =  nowPriceString.split("\\.");
		return splitStr[0];
	}
	
	private String createPriceLable(Price price, String labelType) {
		if (price.getWas() != "") {
			if (labelType.equalsIgnoreCase("ShowWasThenNow")) {
				return showWasThenLabel(price);
			} else if (labelType.equalsIgnoreCase("ShowPercDscount")) {
				return showPercDscount(price);
			} else {
				return showWasNow(price);
			}
		}
		return null;
	}

	/*
	 * Creating price label for ShowPersDscount
	 */
	private String showPercDscount(Price price) {
		if (price.getNow() instanceof String) {
			String percentage = splitPriceValue(Math.ceil(((Double.valueOf(price.getWas().toString()) - 
					Double.valueOf(price.getNow().toString()))/Double.valueOf(price.getWas().toString())) * 100));
			return percentage.concat("% off - now ").concat(priceConversion
					.apply(Double.valueOf((String)price.getNow())));
		}  
		Map<String, String> priceRangeNow = (LinkedHashMap<String, String>)price.getNow();
		Map<String, String> WasRange = (LinkedHashMap<String, String>)price.getWas();
		String percentage = splitPriceValue(Math.ceil(((Double.valueOf(WasRange.get("from")) - 
				Double.valueOf(priceRangeNow.get("from")))/Double.valueOf(WasRange.get("from"))) * 100));
		return percentage.concat("% off - now ").concat(priceConversion
					.apply(Double.valueOf(priceRangeNow.get("from"))));
	}
	
	
	/*
	 * Creating price label for ShowWasNow
	 */
	private String showWasNow(Price price) {
		if(price.getWas() instanceof String && price.getNow() instanceof String) {
			return "Was ".concat(priceConversion
					.apply(Double.valueOf((String)price.getWas())))
					.concat(", now ").concat(priceConversion
					.apply(Double.valueOf((String)price.getNow())));
		} else {
			Map<String, String> priceRangeNow = (LinkedHashMap<String, String>)price.getNow();
			Map<String, String> WasRange = (LinkedHashMap<String, String>)price.getWas();
			return "Was ".concat(priceConversion.apply(Double.valueOf((String)WasRange.get("from")))).concat("-")
					.concat(priceConversion.apply(Double.valueOf((String)WasRange.get("to")))).concat(", now ")
					.concat(priceConversion.apply(Double.valueOf((String)priceRangeNow.get("from")))).concat("-")
					.concat(priceConversion.apply(Double.valueOf((String)priceRangeNow.get("to"))));
		}
	}
	
	/*
	 * Creating price label for ShowWasThenNow
	 */
	private String showWasThenLabel(Price price) {
		if(price.getWas() instanceof String && price.getNow() instanceof String) {
			String priceLabel = "";
			priceLabel = priceLabel.concat("Was ".concat(priceConversion
					.apply(Double.valueOf((String)price.getWas()))));
			if (price.getThen1() != "" || price.getThen2() != "") {
				priceLabel = priceLabel.concat(", then "
						.concat(price.getThen2() != ""?priceConversion
						.apply(Double.valueOf((String)price.getThen2())):priceConversion
								.apply(Double.valueOf((String)price.getThen1()))));
			}
			priceLabel = priceLabel.concat(", now ".concat(priceConversion
					.apply(Double.valueOf((String)price.getNow()))));
			
			return priceLabel;
		} else {
			Map<String, String> priceRangeNow = (LinkedHashMap<String, String>)price.getNow();
			Map<String, String> WasRange = (LinkedHashMap<String, String>)price.getWas();
			Map<String, String> then1Range = price.getThen1() instanceof LinkedHashMap ?(LinkedHashMap<String, String>)price.getThen1() : new LinkedHashMap<>();
			Map<String, String> then2Range = price.getThen2() instanceof LinkedHashMap ?(LinkedHashMap<String, String>)price.getThen2() : new LinkedHashMap<>();
			String priceLabel = "";
			priceLabel = priceLabel.concat("Was ".concat(priceConversion.apply(Double.valueOf((String)WasRange.get("from"))).concat("-")
					.concat(priceConversion.apply(Double.valueOf((String)WasRange.get("to"))))));
			if ((then1Range.size() > 0 && then1Range.get("from") != "") || 
					(then2Range.size() > 0 && then2Range.get("from") !="")) {
				priceLabel = priceLabel.concat(", then ".concat(then2Range.size() > 0 && then2Range.get("from") != ""?priceConversion.apply(Double.valueOf((String)then2Range.get("from"))).concat("-")
						.concat(priceConversion.apply(Double.valueOf((String)then2Range.get("to")))): priceConversion.apply(Double.valueOf((String)then1Range.get("from"))).concat("-")
						.concat(priceConversion.apply(Double.valueOf((String)then1Range.get("to"))))));
			}
			priceLabel = priceLabel.concat(", now ".concat(priceConversion.apply(Double.valueOf((String)priceRangeNow.get("from")))).concat("-")
					.concat(priceConversion.apply(Double.valueOf((String)priceRangeNow.get("to")))));
			return priceLabel;
		}
	}
	
	private Double calculatePriceReduction(Price price) {
		Double priceReduction = 0.00;
		if (price.getWas() != "") {
			if(price.getNow() instanceof String && price.getWas() instanceof String) {
				priceReduction = Double.valueOf(price.getWas().toString()) - Double.valueOf(price.getNow().toString());
			} else {
				Map<String, String> priceRangeNow = (LinkedHashMap<String, String>)price.getNow();
				Map<String, String> WasRange = (LinkedHashMap<String, String>)price.getWas();
				priceReduction = Double.valueOf((String)WasRange.get("from")) - Double.valueOf((String)priceRangeNow.get("from"));
			}
		} 
		return priceReduction;
	}
	/*
	 * API Call
	 */
	public ReducedProducts getProductsWithReducedPrice(String labelType) {
		HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("User-Agent", "test");
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<ReducedProducts> response = restTemplate.exchange(apiUrl, 
        		HttpMethod.GET,entity,ReducedProducts.class);
        Map<String, String> rgbColorMap = assignRgbColor(new HashMap<>());
        if (response.getStatusCode().is2xxSuccessful()) {
        	response.getBody().getProducts().forEach(p -> {
        		p.getColorSwatches().forEach(cs -> cs.setRgbColor(rgbColorMap.get(cs.getRgbColor())));
        		if (p.getPrice().getNow() instanceof String) {
        			p.setNowPrice(priceConversion.apply(Double.valueOf((String)p.getPrice().getNow())));
        		} else {
        			Map<String, String> priceRange = (LinkedHashMap<String, String>)p.getPrice().getNow();
        			p.setNowPrice(priceConversion.apply(Double.valueOf((String)priceRange.get("from"))).concat("-")
        					.concat(priceConversion.apply(Double.valueOf((String)priceRange.get("to")))));
        		}
        		p.setPriceLabel(createPriceLable(p.getPrice(),labelType));
        		p.setPriceReduction(calculatePriceReduction(p.getPrice()));
        	});
        	List<Product> products = response.getBody().getProducts().stream()
        			.filter(p -> p.getPriceReduction() > 0.00)
        			.sorted(Comparator.comparing(Product::getPriceReduction).reversed()).collect(Collectors.toList());
        	ReducedProducts reducedProducts = new ReducedProducts();
        	reducedProducts.setProducts(products);
        	return reducedProducts;
        }
		return null;
	}

	

	

	
}
