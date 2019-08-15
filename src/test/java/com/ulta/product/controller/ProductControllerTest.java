/*
 * Copyright (C) 2019 ULTA
 * http://www.ulta.com
 * BrijendraK@ulta.com
 * All rights reserved
 */
package com.ulta.product.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ulta.product.exception.ProductException;
import com.ulta.product.service.ProductService;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.queries.PagedQueryResult;

@SpringBootTest
public class ProductControllerTest {

	ProductController productController = new ProductController();

	@Mock
	SphereClient client;

	@Mock
	ProductService productService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		// productController.setSphereClient(client);
		productController.setProductService(productService);
	}

	@Test(expected = ProductException.class)
	public void testgetProductBykeyWhenProductGetisNull() {

		CompletableFuture<Product> products = new CompletableFuture<Product>();
		Product value = null;
		products.complete(value);
		String key = "Liquid-exception";
		when(productService.getProductByKey(key)).thenReturn(products);
		productController.getProductByKey(key);
	}

	@Test()
	public void testgetProductBykey() {

		CompletableFuture<Product> products = new CompletableFuture<Product>();
		Product value = Mockito.mock(Product.class);
		products.complete(value);
		String key = "Liquid";
		when(productService.getProductByKey(key)).thenReturn(products);
		ResponseEntity<Product> result = productController.getProductByKey(key);
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test()
	public void testgetProducts() {

		CompletableFuture<PagedQueryResult<ProductProjection>> products = new CompletableFuture<PagedQueryResult<ProductProjection>>();
		ProductProjection productProjection = Mockito.mock(ProductProjection.class);
		@SuppressWarnings("unchecked")
		PagedQueryResult<ProductProjection> value = Mockito.mock(PagedQueryResult.class);
		value.getResults().add(productProjection);
		products.complete(value);
		when(productService.getProducts()).thenReturn(products);
		ResponseEntity<List<ProductProjection>> result = productController.getProducts();
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

}
