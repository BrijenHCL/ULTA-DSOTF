/*
 * Copyright (C) 2019 ULTA
 * http://www.ulta.com
 * BrijendraK@ulta.com
 * All rights reserved
 */
package com.ulta.product.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.queries.PagedQueryResult;

public interface ProductService {

	public CompletableFuture<Product> getProductByKey(String key);

	public CompletableFuture<PagedQueryResult<ProductProjection>> getProducts();

	public CompletableFuture<PagedQueryResult<ProductProjection>> findProductsWithCategory(String ctgId)
			throws InterruptedException, ExecutionException;
}
