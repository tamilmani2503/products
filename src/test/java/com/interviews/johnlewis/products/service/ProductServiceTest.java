package com.interviews.johnlewis.products.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.interviews.johnlewis.products.model.ColorSwatch;
import com.interviews.johnlewis.products.model.Price;
import com.interviews.johnlewis.products.model.Product;
import com.interviews.johnlewis.products.model.ReducedProducts;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
	
	@InjectMocks
	ProductService productService;
	
	@Mock
	RestTemplate restTemplate;
	
	
	
	@Test
	public void checkDefaultProductList_successAPIcall() throws Exception {
		List<ColorSwatch> colorSwatches = Arrays.asList(new ColorSwatch("Blue","oxfb123","3553"));
		ReducedProducts products = new ReducedProducts(Arrays.asList(new Product("567","tshirt"
				,colorSwatches,"£12","", new Price("12.99","","","11.99"))));
		Mockito
			.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class), Mockito.eq(ReducedProducts.class)))
			.thenReturn(new ResponseEntity<>(products, HttpStatus.OK));
		ReducedProducts reducedProducts = productService.getProductsWithReducedPrice("ShowWasNow");
		assertEquals(products.getProducts().size() > 0, reducedProducts.getProducts().size() > 0);
	}
	
	@Test
	public void checkDefaultProductList_rgbColorConversion() throws Exception {
		List<ColorSwatch> colorSwatchesActual = Arrays.asList(new ColorSwatch("Dusty Pink","Pink","3553"));
		ReducedProducts productsActual = new ReducedProducts(Arrays.asList(new Product("567","tshirt"
				,colorSwatchesActual,"£12","", new Price("12.99","","","11.99"))));
		
		List<ColorSwatch> colorSwatchesExpected = Arrays.asList(new ColorSwatch("Dusty Pink","FFC0CB","3553"));
		ReducedProducts productsExpected = new ReducedProducts(Arrays.asList(new Product("567","tshirt"
				,colorSwatchesExpected,"£12","", new Price("12.99","","","11.99"))));
		Mockito
			.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class), Mockito.eq(ReducedProducts.class)))
			.thenReturn(new ResponseEntity<>(productsActual, HttpStatus.OK));
		ReducedProducts reducedProducts = productService.getProductsWithReducedPrice("ShowWasNow");
		assertEquals(productsExpected.getProducts().get(0).getColorSwatches().get(0)
				.getRgbColor(), 
				reducedProducts.getProducts().get(0).getColorSwatches()
				.get(0).getRgbColor());
	}
	
	@Test
	public void checkDefaultProductList_rgbColorConversion_Multi() throws Exception {
		List<ColorSwatch> colorSwatchesActual = Arrays.asList(new ColorSwatch("Multi","Multi","3553"));
		ReducedProducts productsActual = new ReducedProducts(Arrays.asList(new Product("567","tshirt"
				,colorSwatchesActual,"£12","",new Price("12.99","","","11.99"))));
		
		List<ColorSwatch> colorSwatchesExpected = Arrays.asList(new ColorSwatch("Multi","xxxxxx","3553"));
		ReducedProducts productsExpected = new ReducedProducts(Arrays.asList(new Product("567","tshirt"
				,colorSwatchesExpected,"£12","", new Price("12.99","","","11.99"))));
		Mockito
			.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class), Mockito.eq(ReducedProducts.class)))
			.thenReturn(new ResponseEntity<>(productsActual, HttpStatus.OK));
		ReducedProducts reducedProducts = productService.getProductsWithReducedPrice("ShowWasNow");
		assertEquals(productsExpected.getProducts().get(0).getColorSwatches().get(0)
				.getRgbColor(), 
				reducedProducts.getProducts().get(0).getColorSwatches()
				.get(0).getRgbColor());
	}
	
	@Test
	public void checkDefaultProductList_nowPriceIntegerValue() throws Exception {
		List<ColorSwatch> colorSwatchesActual = Arrays.asList(new ColorSwatch("Multi","Multi","3553"));
		ReducedProducts productsActual = new ReducedProducts(Arrays.asList(new Product("567","tshirt"
				,colorSwatchesActual,"","", new Price("12.99","","","11.99"))));
		
		List<ColorSwatch> colorSwatchesExpected = Arrays.asList(new ColorSwatch("Multi","xxxxxx","3553"));
		ReducedProducts productsExpected = new ReducedProducts(Arrays.asList(new Product("567","tshirt"
				,colorSwatchesExpected,"£11","", new Price("12.99","","","11.99"))));
		Mockito
			.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class), Mockito.eq(ReducedProducts.class)))
			.thenReturn(new ResponseEntity<>(productsActual, HttpStatus.OK));
		ReducedProducts reducedProducts = productService.getProductsWithReducedPrice("ShowWasNow");
		assertEquals(productsExpected.getProducts().get(0).getNowPrice(), 
				reducedProducts.getProducts().get(0).getNowPrice());
	}
	
	@Test
	public void checkDefaultProductList_nowPriceWithDecimalValue() throws Exception {
		List<ColorSwatch> colorSwatchesActual = Arrays.asList(new ColorSwatch("Multi","Multi","3553"));
		ReducedProducts productsActual = new ReducedProducts(Arrays.asList(new Product("567","tshirt"
				,colorSwatchesActual,"","", new Price("12.99","","","9.99"))));
		
		List<ColorSwatch> colorSwatchesExpected = Arrays.asList(new ColorSwatch("Multi","xxxxxx","3553"));
		ReducedProducts productsExpected = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatchesExpected
				,"£9.99","", new Price("12.99","","","9.99"))));
		Mockito
			.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class), Mockito.eq(ReducedProducts.class)))
			.thenReturn(new ResponseEntity<>(productsActual, HttpStatus.OK));
		ReducedProducts reducedProducts = productService.getProductsWithReducedPrice("ShowWasNow");
		assertEquals(productsExpected.getProducts().get(0).getNowPrice(), 
				reducedProducts.getProducts().get(0).getNowPrice());
	}
	
	@Test
	public void checkDefaultProductList_nowPriceWithPriceRangeIntegerValue() throws Exception {
		List<ColorSwatch> colorSwatchesActual = Arrays.asList(new ColorSwatch("Multi","Multi","3553"));
		Map<String, String> nowPriceRangeMap =  new LinkedHashMap<>();
		nowPriceRangeMap.put("from", "12.99");
		nowPriceRangeMap.put("to", "15.99");
		Map<String, String> wasPriceRangeMap =  new LinkedHashMap<>();
		wasPriceRangeMap.put("from", "15.99");
		wasPriceRangeMap.put("to", "18.99");
		
		ReducedProducts productsActual = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatchesActual
				,"","", new Price(wasPriceRangeMap,"","",nowPriceRangeMap))));
		
		List<ColorSwatch> colorSwatchesExpected = Arrays.asList(new ColorSwatch("Multi","xxxxxx","3553"));
		ReducedProducts productsExpected = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatchesExpected
				,"£12-£15","", new Price(wasPriceRangeMap,"","",nowPriceRangeMap))));
		Mockito
			.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class), Mockito.eq(ReducedProducts.class)))
			.thenReturn(new ResponseEntity<>(productsActual, HttpStatus.OK));
		ReducedProducts reducedProducts = productService.getProductsWithReducedPrice("ShowWasNow");
		assertEquals(productsExpected.getProducts().get(0).getNowPrice(), 
				reducedProducts.getProducts().get(0).getNowPrice());
	}
	
	@Test
	public void checkDefaultProductList_nowPriceWithPriceRangeDecimalValue() throws Exception {
		List<ColorSwatch> colorSwatchesActual = Arrays.asList(new ColorSwatch("Multi","Multi","3553"));
		Map<String, String> nowPriceRangeMap =  new LinkedHashMap<>();
		nowPriceRangeMap.put("from", "7.99");
		nowPriceRangeMap.put("to", "9.99");
		Map<String, String> wasPriceRangeMap =  new LinkedHashMap<>();
		wasPriceRangeMap.put("from", "10.99");
		wasPriceRangeMap.put("to", "12.99");
		ReducedProducts productsActual = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatchesActual
				,"","", new Price(wasPriceRangeMap,"","",nowPriceRangeMap))));
		
		List<ColorSwatch> colorSwatchesExpected = Arrays.asList(new ColorSwatch("Multi","xxxxxx","3553"));
		ReducedProducts productsExpected = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatchesExpected
				,"£7.99-£9.99","", new Price(wasPriceRangeMap,"","",nowPriceRangeMap))));
		Mockito
			.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class), Mockito.eq(ReducedProducts.class)))
			.thenReturn(new ResponseEntity<>(productsActual, HttpStatus.OK));
		ReducedProducts reducedProducts = productService.getProductsWithReducedPrice("ShowWasNow");
		assertEquals(productsExpected.getProducts().get(0).getNowPrice(), 
				reducedProducts.getProducts().get(0).getNowPrice());
	}
	
	@Test
	public void checkDefaultProductList_checkShowWasNowFormat() throws Exception {
		List<ColorSwatch> colorSwatchesActual = Arrays.asList(new ColorSwatch("Multi","Multi","3553"));
		ReducedProducts productsActual = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatchesActual
				,"","", new Price("12.99","","","9.99"))));
		
		List<ColorSwatch> colorSwatchesExpected = Arrays.asList(new ColorSwatch("Multi","xxxxxx","3553"));
		ReducedProducts productsExpected = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatchesExpected
				,"£7.99-£9.99","Was £12, now £9.99", new Price("12.99","","","9.99"))));
		Mockito
			.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class), Mockito.eq(ReducedProducts.class)))
			.thenReturn(new ResponseEntity<>(productsActual, HttpStatus.OK));
		ReducedProducts reducedProducts = productService.getProductsWithReducedPrice("ShowWasNow");
		assertEquals(productsExpected.getProducts().get(0).getPriceLabel(), 
				reducedProducts.getProducts().get(0).getPriceLabel());
	}
	
	@Test
	public void checkDefaultProductList_checkShowWasNowFormatPriceRange() throws Exception {
		List<ColorSwatch> colorSwatchesActual = Arrays.asList(new ColorSwatch("Multi","Multi","3553"));
		Map<String, String> nowPriceRangeMap =  new LinkedHashMap<>();
		nowPriceRangeMap.put("from", "7.99");
		nowPriceRangeMap.put("to", "9.99");
		Map<String, String> wasPriceRangeMap =  new LinkedHashMap<>();
		wasPriceRangeMap.put("from", "12.99");
		wasPriceRangeMap.put("to", "15.99");
		ReducedProducts productsActual = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatchesActual
				,"","", new Price(wasPriceRangeMap, "", "", nowPriceRangeMap))));
		
		List<ColorSwatch> colorSwatchesExpected = Arrays.asList(new ColorSwatch("Multi","xxxxxx","3553"));
		ReducedProducts productsExpected = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatchesExpected
				,"£7.99-£9.99","Was £12-£15, now £7.99-£9.99", new Price(wasPriceRangeMap,"","",nowPriceRangeMap))));
		Mockito
			.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class), Mockito.eq(ReducedProducts.class)))
			.thenReturn(new ResponseEntity<>(productsActual, HttpStatus.OK));
		ReducedProducts reducedProducts = productService.getProductsWithReducedPrice("ShowWasNow");
		assertEquals(productsExpected.getProducts().get(0).getPriceLabel(), 
				reducedProducts.getProducts().get(0).getPriceLabel());
	}
	
	
	@Test
	public void checkDefaultProductList_checkShowWasNowFormatSortByReduction() throws Exception {
		List<ColorSwatch> colorSwatchesActual = Arrays.asList(new ColorSwatch("Multi","Multi","3553"));
		ReducedProducts productsActual = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatchesActual
				,"","", new Price("12.99","","","9.99")),
				new Product("568","tshirt",colorSwatchesActual
						,"","", new Price("12.99","","","6.99"))));
		
		List<ColorSwatch> colorSwatchesExpected = Arrays.asList(new ColorSwatch("Multi","xxxxxx","3553"));
		ReducedProducts productsExpected = new ReducedProducts(Arrays.asList(new Product("568","tshirt",colorSwatchesActual
				,"","", new Price("12.99","","","6.99")),new Product("567","tshirt",colorSwatchesExpected
				,"£7.99-£9.99","Was £12, now £9.99", new Price("12.99","","","9.99"))));
		Mockito
			.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class), Mockito.eq(ReducedProducts.class)))
			.thenReturn(new ResponseEntity<>(productsActual, HttpStatus.OK));
		ReducedProducts reducedProducts = productService.getProductsWithReducedPrice("ShowWasNow");
		assertEquals(productsExpected.getProducts().get(0).getProductId(), 
				reducedProducts.getProducts().get(0).getProductId());
	}
	
	@Test
	public void checkDefaultProductList_checkShowWasNowFormatSortByReductionWasPriceEmpty() throws Exception {
		List<ColorSwatch> colorSwatchesActual = Arrays.asList(new ColorSwatch("Multi","Multi","3553"));
		ReducedProducts productsActual = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatchesActual
				,"","", new Price("12.99","","","9.99")),
				new Product("568","tshirt",colorSwatchesActual
						,"","", new Price("","","","6.99"))));
		
		List<ColorSwatch> colorSwatchesExpected = Arrays.asList(new ColorSwatch("Multi","xxxxxx","3553"));
		ReducedProducts productsExpected = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatchesExpected
				,"£7.99-£9.99","Was £12 now £9.99", new Price("12.99","","","9.99")),new Product("568","tshirt",colorSwatchesActual
				,"","", new Price("","","","6.99"))));
		Mockito
			.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class), Mockito.eq(ReducedProducts.class)))
			.thenReturn(new ResponseEntity<>(productsActual, HttpStatus.OK));
		ReducedProducts reducedProducts = productService.getProductsWithReducedPrice("ShowWasNow");
		assertEquals(productsExpected.getProducts().get(0).getProductId(), 
				reducedProducts.getProducts().get(0).getProductId());
	}
	
	@Test
	public void checkDefaultProductList_checkShowWasNowFormatSortByReductionWithReducedProductsonly() throws Exception {
		List<ColorSwatch> colorSwatchesActual = Arrays.asList(new ColorSwatch("Multi","Multi","3553"));
		ReducedProducts productsActual = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatchesActual
				,"","", new Price("12.99","","","9.99")),
				new Product("568","tshirt",colorSwatchesActual
						,"","", new Price("","","","6.99"))));
		
		List<ColorSwatch> colorSwatchesExpected = Arrays.asList(new ColorSwatch("Multi","xxxxxx","3553"));
		ReducedProducts productsExpected = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatchesExpected
				,"£7.99-£9.99","Was £12 now £9.99", new Price("12.99","","","9.99"))));
		Mockito
			.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class), Mockito.eq(ReducedProducts.class)))
			.thenReturn(new ResponseEntity<>(productsActual, HttpStatus.OK));
		ReducedProducts reducedProducts = productService.getProductsWithReducedPrice("ShowWasNow");
		assertEquals(productsExpected.getProducts().size(), 
				reducedProducts.getProducts().size());
	}
	
	@Test
	public void checkDefaultProductList_checkShowWasNowFormat_PriceRange_SortByReductionWithReducedProductsonly() throws Exception {
		List<ColorSwatch> colorSwatchesActual = Arrays.asList(new ColorSwatch("Multi","Multi","3553"));
		Map<String, String> nowPriceRangeMap =  new LinkedHashMap<>();
		nowPriceRangeMap.put("from", "7.99");
		nowPriceRangeMap.put("to", "9.99");
		Map<String, String> wasPriceRangeMap =  new LinkedHashMap<>();
		wasPriceRangeMap.put("from", "12.99");
		wasPriceRangeMap.put("to", "15.99");
		
		Map<String, String> nowPriceRangeMapSecond =  new LinkedHashMap<>();
		nowPriceRangeMapSecond.put("from", "5.99");
		nowPriceRangeMapSecond.put("to", "9.99");
		Map<String, String> wasPriceRangeMapSecond =  new LinkedHashMap<>();
		wasPriceRangeMapSecond.put("from", "7.99");
		wasPriceRangeMapSecond.put("to", "12.99");
		ReducedProducts productsActual = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatchesActual
				,"","", new Price(wasPriceRangeMap, "", "", nowPriceRangeMap)),
				new Product("568","tshirt",colorSwatchesActual
						,"","", new Price(wasPriceRangeMapSecond, "", "", nowPriceRangeMapSecond)),
				new Product("569","tshirt",colorSwatchesActual
						,"","", new Price("20", "", "", "10"))));
		
		List<ColorSwatch> colorSwatchesExpected = Arrays.asList(new ColorSwatch("Multi","xxxxxx","3553"));
		ReducedProducts productsExpected = new ReducedProducts(Arrays.asList(new Product("569","tshirt",colorSwatchesActual
				,"","", new Price("20", "", "", "10")),new Product("567","tshirt",colorSwatchesExpected
				,"£7.99-£9.99","Was £12-£15 now £7.99-£9.99", new Price(wasPriceRangeMap,"","",nowPriceRangeMap)),
				new Product("568","tshirt",colorSwatchesActual
						,"","", new Price(wasPriceRangeMapSecond, "", "", nowPriceRangeMapSecond))));
		Mockito
			.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class), Mockito.eq(ReducedProducts.class)))
			.thenReturn(new ResponseEntity<>(productsActual, HttpStatus.OK));
		ReducedProducts reducedProducts = productService.getProductsWithReducedPrice("ShowWasNow");
		assertEquals(productsExpected.getProducts().get(0).getProductId(), 
				reducedProducts.getProducts().get(0).getProductId());
		assertSame("568", reducedProducts.getProducts().get(2).getProductId());
	}
	
	//ShowwasThenNow - Then1
	@Test
	public void checkDefaultProductList_checkShowWasThenNowThen1Format() throws Exception {
		List<ColorSwatch> colorSwatchesActual = Arrays.asList(new ColorSwatch("Multi","Multi","3553"));
		ReducedProducts productsActual = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatchesActual
				,"","", new Price("12.99","10.99","","9.99"))));
		
		List<ColorSwatch> colorSwatchesExpected = Arrays.asList(new ColorSwatch("Multi","xxxxxx","3553"));
		ReducedProducts productsExpected = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatchesExpected
				,"£7.99-£9.99","Was £12, then £10, now £9.99", new Price("12.99","10.99","","9.99"))));
		Mockito
			.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class), Mockito.eq(ReducedProducts.class)))
			.thenReturn(new ResponseEntity<>(productsActual, HttpStatus.OK));
		ReducedProducts reducedProducts = productService.getProductsWithReducedPrice("ShowWasThenNow");
		assertEquals(productsExpected.getProducts().get(0).getPriceLabel(), 
				reducedProducts.getProducts().get(0).getPriceLabel());
	}
	//ShowwasThenNow - Then2
	@Test
	public void checkDefaultProductList_checkShowWasThenNowThen2Format() throws Exception {
		List<ColorSwatch> colorSwatchesActual = Arrays.asList(new ColorSwatch("Multi","Multi","3553"));
		ReducedProducts productsActual = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatchesActual
				,"","", new Price("12.99","12.59","11.59","9.99"))));
		
		List<ColorSwatch> colorSwatchesExpected = Arrays.asList(new ColorSwatch("Multi","xxxxxx","3553"));
		ReducedProducts productsExpected = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatchesExpected
				,"£7.99-£9.99","Was £12, then £11, now £9.99", new Price("12.99","12.59","11.59","9.99"))));
		Mockito
			.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class), Mockito.eq(ReducedProducts.class)))
			.thenReturn(new ResponseEntity<>(productsActual, HttpStatus.OK));
		ReducedProducts reducedProducts = productService.getProductsWithReducedPrice("ShowWasThenNow");
		assertEquals(productsExpected.getProducts().get(0).getPriceLabel(), 
				reducedProducts.getProducts().get(0).getPriceLabel());
	}
	
	//ShowwasThenNow - Now
	@Test
	public void checkDefaultProductList_checkShowWasThenNowFormat() throws Exception {
		List<ColorSwatch> colorSwatchesActual = Arrays.asList(new ColorSwatch("Multi","Multi","3553"));
		ReducedProducts productsActual = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatchesActual
				,"","", new Price("12.99","","","9.99"))));
		
		List<ColorSwatch> colorSwatchesExpected = Arrays.asList(new ColorSwatch("Multi","xxxxxx","3553"));
		ReducedProducts productsExpected = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatchesExpected
				,"£7.99-£9.99","Was £12, now £9.99", new Price("12.99","","","9.99"))));
		Mockito
			.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class), Mockito.eq(ReducedProducts.class)))
			.thenReturn(new ResponseEntity<>(productsActual, HttpStatus.OK));
		ReducedProducts reducedProducts = productService.getProductsWithReducedPrice("ShowWasThenNow");
		assertEquals(productsExpected.getProducts().get(0).getPriceLabel(), 
				reducedProducts.getProducts().get(0).getPriceLabel());
	}
	
	//ShowwasThenRange - Then1
	@Test
	public void checkDefaultProductList_checkShowWasThen1FormatPriceRange() throws Exception {
		List<ColorSwatch> colorSwatchesActual = Arrays.asList(new ColorSwatch("Multi","Multi","3553"));
		Map<String, String> nowPriceRangeMap =  new LinkedHashMap<>();
		nowPriceRangeMap.put("from", "7.99");
		nowPriceRangeMap.put("to", "9.99");
		Map<String, String> wasPriceRangeMap =  new LinkedHashMap<>();
		wasPriceRangeMap.put("from", "12.99");
		wasPriceRangeMap.put("to", "15.99");
		Map<String, String> then1PriceRangeMap =  new LinkedHashMap<>();
		then1PriceRangeMap.put("from", "11.99");
		then1PriceRangeMap.put("to", "14.99");
		ReducedProducts productsActual = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatchesActual
				,"","", new Price(wasPriceRangeMap, then1PriceRangeMap, "", nowPriceRangeMap))));
		
		List<ColorSwatch> colorSwatchesExpected = Arrays.asList(new ColorSwatch("Multi","xxxxxx","3553"));
		ReducedProducts productsExpected = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatchesExpected
				,"£7.99-£9.99","Was £12-£15, then £11-£14, now £7.99-£9.99", new Price(wasPriceRangeMap,then1PriceRangeMap,"",nowPriceRangeMap))));
		Mockito
			.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class), Mockito.eq(ReducedProducts.class)))
			.thenReturn(new ResponseEntity<>(productsActual, HttpStatus.OK));
		ReducedProducts reducedProducts = productService.getProductsWithReducedPrice("ShowWasThenNow");
		assertEquals(productsExpected.getProducts().get(0).getPriceLabel(), 
				reducedProducts.getProducts().get(0).getPriceLabel());
	}
	
	//ShowwasThenRange - Then2
	@Test
	public void checkDefaultProductList_checkShowWasThen2FormatPriceRange() throws Exception {
		List<ColorSwatch> colorSwatchesActual = Arrays.asList(new ColorSwatch("Multi","Multi","3553"));
		Map<String, String> nowPriceRangeMap =  new LinkedHashMap<>();
		nowPriceRangeMap.put("from", "7.99");
		nowPriceRangeMap.put("to", "9.99");
		Map<String, String> wasPriceRangeMap =  new LinkedHashMap<>();
		wasPriceRangeMap.put("from", "12.99");
		wasPriceRangeMap.put("to", "15.99");
		Map<String, String> then1PriceRangeMap =  new LinkedHashMap<>();
		then1PriceRangeMap.put("from", "11.99");
		then1PriceRangeMap.put("to", "14.99");
		Map<String, String> then2PriceRangeMap =  new LinkedHashMap<>();
		then2PriceRangeMap.put("from", "10.99");
		then2PriceRangeMap.put("to", "13.99");
		ReducedProducts productsActual = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatchesActual
				,"","", new Price(wasPriceRangeMap, then1PriceRangeMap, then2PriceRangeMap, nowPriceRangeMap))));
		
		List<ColorSwatch> colorSwatchesExpected = Arrays.asList(new ColorSwatch("Multi","xxxxxx","3553"));
		ReducedProducts productsExpected = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatchesExpected
				,"£7.99-£9.99","Was £12-£15, then £10-£13, now £7.99-£9.99", new Price(wasPriceRangeMap,then1PriceRangeMap,then2PriceRangeMap,nowPriceRangeMap))));
		Mockito
			.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class), Mockito.eq(ReducedProducts.class)))
			.thenReturn(new ResponseEntity<>(productsActual, HttpStatus.OK));
		ReducedProducts reducedProducts = productService.getProductsWithReducedPrice("ShowWasThenNow");
		assertEquals(productsExpected.getProducts().get(0).getPriceLabel(), 
				reducedProducts.getProducts().get(0).getPriceLabel());
	}
	
	//ShowwasThenRange - Now
	@Test
	public void checkDefaultProductList_checkShowWasThenNowFormatPriceRange() throws Exception {
		List<ColorSwatch> colorSwatchesActual = Arrays.asList(new ColorSwatch("Multi","Multi","3553"));
		Map<String, String> nowPriceRangeMap =  new LinkedHashMap<>();
		nowPriceRangeMap.put("from", "7.99");
		nowPriceRangeMap.put("to", "9.99");
		Map<String, String> wasPriceRangeMap =  new LinkedHashMap<>();
		wasPriceRangeMap.put("from", "12.99");
		wasPriceRangeMap.put("to", "15.99");
		ReducedProducts productsActual = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatchesActual
				,"","", new Price(wasPriceRangeMap, "", "", nowPriceRangeMap))));
		
		List<ColorSwatch> colorSwatchesExpected = Arrays.asList(new ColorSwatch("Multi","xxxxxx","3553"));
		ReducedProducts productsExpected = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatchesExpected
				,"£7.99-£9.99","Was £12-£15, now £7.99-£9.99", new Price(wasPriceRangeMap,"","",nowPriceRangeMap))));
		Mockito
			.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class), Mockito.eq(ReducedProducts.class)))
			.thenReturn(new ResponseEntity<>(productsActual, HttpStatus.OK));
		ReducedProducts reducedProducts = productService.getProductsWithReducedPrice("ShowWasThenNow");
		assertEquals(productsExpected.getProducts().get(0).getPriceLabel(), 
				reducedProducts.getProducts().get(0).getPriceLabel());
	}
	
	//Show Discount Percent 
	@Test
	public void checkDefaultProductList_checkShowPesDiscount() throws Exception {
		List<ColorSwatch> colorSwatchesActual = Arrays.asList(new ColorSwatch("Multi","Multi","3553"));
		ReducedProducts productsActual = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatchesActual
				,"","", new Price("12.99","","","9.99"))));
		
		List<ColorSwatch> colorSwatchesExpected = Arrays.asList(new ColorSwatch("Multi","xxxxxx","3553"));
		ReducedProducts productsExpected = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatchesExpected
				,"£7.99-£9.99","24% off - now £9.99", new Price("12.99","","","9.99"))));
		Mockito
			.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class), Mockito.eq(ReducedProducts.class)))
			.thenReturn(new ResponseEntity<>(productsActual, HttpStatus.OK));
		ReducedProducts reducedProducts = productService.getProductsWithReducedPrice("ShowPercDscount");
		assertEquals(productsExpected.getProducts().get(0).getPriceLabel(), 
				reducedProducts.getProducts().get(0).getPriceLabel());
	}
	
	@Test
	public void checkDefaultProductList_checkShowPesDiscountPriceRange() throws Exception {
		Map<String, String> nowPriceRangeMap =  new LinkedHashMap<>();
		nowPriceRangeMap.put("from", "6.55");
		nowPriceRangeMap.put("to", "9.99");
		Map<String, String> wasPriceRangeMap =  new LinkedHashMap<>();
		wasPriceRangeMap.put("from", "12.99");
		wasPriceRangeMap.put("to", "15.99");
		List<ColorSwatch> colorSwatchesActual = Arrays.asList(new ColorSwatch("Multi","Multi","3553"));
		ReducedProducts productsActual = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatchesActual
				,"","", new Price(wasPriceRangeMap,"","",nowPriceRangeMap))));
		
		List<ColorSwatch> colorSwatchesExpected = Arrays.asList(new ColorSwatch("Multi","xxxxxx","3553"));
		ReducedProducts productsExpected = new ReducedProducts(Arrays.asList(new Product("567","tshirt",colorSwatchesExpected
				,"£7.99-£9.99","50% off - now £6.55", new Price(wasPriceRangeMap,"","",nowPriceRangeMap))));
		Mockito
			.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class), Mockito.eq(ReducedProducts.class)))
			.thenReturn(new ResponseEntity<>(productsActual, HttpStatus.OK));
		ReducedProducts reducedProducts = productService.getProductsWithReducedPrice("ShowPercDscount");
		assertEquals(productsExpected.getProducts().get(0).getPriceLabel(), 
				reducedProducts.getProducts().get(0).getPriceLabel());
	}
}
