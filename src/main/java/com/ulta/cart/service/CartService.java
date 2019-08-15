/*
 * Copyright (C) 2019 ULTA
 * http://www.ulta.com
 * BrijendraK@ulta.com
 * All rights reserved
 */
package com.ulta.cart.service;

import org.springframework.stereotype.Service;

import com.ulta.cart.request.CreateCartRequest;

import io.sphere.sdk.carts.Cart;

@Service
public interface CartService {

	public Cart addToCart(CreateCartRequest requestDto) throws Exception;;
}
