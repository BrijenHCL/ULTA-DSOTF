/*
 * Copyright (C) 2019 ULTA
 * http://www.ulta.com
 * BrijendraK@ulta.com
 * All rights reserved
 */
package com.ulta.cart.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ulta.cart.exception.CartException;
import com.ulta.cart.request.CreateCartRequest;
import com.ulta.cart.service.CartService;

import io.sphere.sdk.carts.Cart;

@RequestMapping("/cart/v1")
@RestController
public class CartController {

	@Autowired
	private CartService cartService;

	static Logger log = LoggerFactory.getLogger(CartController.class);

	/**
	 * 
	 * @param requestDto
	 * @return
	 * @throws CartException
	 */
	@RequestMapping(path = "/addItemToCart", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Cart> addItemToCart(@RequestBody CreateCartRequest requestDto) throws CartException {
		log.info("Add Item to Cart Start");
		Cart fetchedCart = null;
		try {
			fetchedCart = cartService.addToCart(requestDto);

		} catch (Exception e) {
			log.error("exception during adding item to cart, details-" + e.getMessage());
			throw new CartException(e.getMessage());
		}
		log.info("Add Item to Cart End");
		return ResponseEntity.ok().body(fetchedCart);
	}

	public CartService getCartService() {
		return cartService;
	}

	public void setCartService(CartService cartService) {
		this.cartService = cartService;
	}

}
