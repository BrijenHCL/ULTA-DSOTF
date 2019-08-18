/*
 * Copyright (C) 2019 ULTA
 * http://www.ulta.com
 * BrijendraK@ulta.com
 * All rights reserved
 */
package com.ulta.product.serviceImpl;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulta.product.exception.ProductException;
import com.ulta.product.service.ProductService;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.queries.CategoryByKeyGet;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.queries.ProductByKeyGet;
import io.sphere.sdk.products.queries.ProductProjectionQuery;
import io.sphere.sdk.queries.PagedQueryResult;

@Service
public class ProductServiceImpl implements ProductService {
	static Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
	@Autowired
	SphereClient client;

	@Override
	public CompletableFuture<Product> getProductByKey(String key) throws ProductException {
		log.info("getProductByKey method start");
		final ProductByKeyGet request = ProductByKeyGet.of(key);
		CompletionStage<Product> pro = client.execute(request);
		CompletableFuture<Product> returnProduct = null;
		if (null != pro) {
			returnProduct = pro.toCompletableFuture();
		} else
			throw new ProductException("Product Data is empty");

		log.info("getProductByKey method end");
		return returnProduct;
	}

	@Override
	public CompletableFuture<PagedQueryResult<ProductProjection>> getProducts() {
		log.info("getProducts method start");

		final ProductProjectionQuery pro = ProductProjectionQuery.ofCurrent();
		CompletionStage<PagedQueryResult<ProductProjection>> result = client.execute(pro);
		CompletableFuture<PagedQueryResult<ProductProjection>> returnProduct = null;
		if (null != result) {
			returnProduct = result.toCompletableFuture();
		} else
			throw new ProductException("Product Data is empty");

		log.info("getProducts method end");
		return returnProduct;
	}

	@Override
	public CompletableFuture<PagedQueryResult<ProductProjection>> findProductsWithCategory(String categorykey)
			throws InterruptedException, ExecutionException {
		log.info("findProductsWithCategory method start");
		
		
		CompletionStage<Category> category = client
				.execute(CategoryByKeyGet.of(categorykey));
		CompletableFuture<PagedQueryResult<ProductProjection>> returnProductwithcategory = null;
		if(null!=category.toCompletableFuture()) {
			
		CompletableFuture<Category> returnCat =  category.toCompletableFuture();
		Category category2 =  returnCat.get();
		ProductProjectionQuery exists = ProductProjectionQuery.ofCurrent()
				.withPredicates(m -> m.categories().isIn(Arrays.asList(category2)));
		CompletionStage<PagedQueryResult<ProductProjection>> productsWithCategory = client.execute(exists);
		
		if (null != productsWithCategory) {
			returnProductwithcategory = productsWithCategory.toCompletableFuture();
		} else
			throw new ProductException("Product With Category is empty");
		}
		
		log.info("findProductsWithCategory method end");
		return returnProductwithcategory;
	}

	public void setClient(SphereClient client) {
		this.client=client;
	}

}
