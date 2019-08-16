/*
 * Copyright (C) 2019 ULTA
 * http://www.ulta.com
 * BrijendraK@ulta.com
 * All rights reserved
 */
package com.ulta.cart.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import com.ulta.cart.exception.CartException;
import com.ulta.cart.request.CreateCartRequest;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.queries.PagedQueryResult;

@Service
public interface CartService {

	public Cart addToCart(CreateCartRequest requestDto) throws CartException;
	public CompletableFuture<PagedQueryResult<Cart>> getAllCarts();
}
