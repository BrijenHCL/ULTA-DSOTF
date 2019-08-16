/*
 * Copyright (C) 2019 ULTA
 * http://www.ulta.com
 * BrijendraK@ulta.com
 * All rights reserved
 */
package com.ulta.product.serviceImpl;

import static org.mockito.Mockito.when;

import java.util.concurrent.CompletionStage;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.ulta.product.exception.ProductException;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.queries.ProductByKeyGet;
import io.sphere.sdk.products.queries.ProductProjectionQuery;
import io.sphere.sdk.queries.PagedQueryResult;

@SpringBootTest
public class ProductServiceImplTest {
	ProductServiceImpl productServiceImpl = new ProductServiceImpl();

	@Mock
	SphereClient client;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		productServiceImpl.setClient(client);
	}

	@Test
	public void testGetProductByKey() {
		String key = "facewash";
		ProductByKeyGet request = ProductByKeyGet.of(key);
		CompletionStage<Product> value = (CompletionStage<Product>) Mockito.mock(CompletionStage.class);
		when(client.execute(request)).thenReturn(value);
		productServiceImpl.getProductByKey(key);
	}

	@Test(expected = ProductException.class)
	public void testGetProductByKeyForExceptionWhenProductIsNull() {
		String key = "facewash";
		ProductByKeyGet request = ProductByKeyGet.of(key);
		when(client.execute(request)).thenReturn(null);
		productServiceImpl.getProductByKey(key);
	}

	@Test
	public void testGetProductsSucessCase() {
		final ProductProjectionQuery pro = ProductProjectionQuery.ofCurrent();
		CompletionStage<PagedQueryResult<ProductProjection>> value = (CompletionStage<PagedQueryResult<ProductProjection>>) Mockito
				.mock(CompletionStage.class);
		when(client.execute(pro)).thenReturn(value);
		productServiceImpl.getProducts();
	}

	@Test(expected = ProductException.class)
	public void testGetProductsWhenProductDataIsNull() {
		final ProductProjectionQuery pro = ProductProjectionQuery.ofCurrent();
		CompletionStage<PagedQueryResult<ProductProjection>> value = (CompletionStage<PagedQueryResult<ProductProjection>>) Mockito
				.mock(CompletionStage.class);
		when(client.execute(pro)).thenReturn(null);
		productServiceImpl.getProducts();
	}
}
