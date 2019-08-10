package com.huunghia.service;

import java.util.List;

import com.huunghia.entity.Book;
import com.huunghia.entity.CartItem;
import com.huunghia.entity.ShoppingCart;
import com.huunghia.entity.User;

public interface CartItemService {
	CartItem addBookToCartItem(Book book, User user, int qty);
	
	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
	
	List<CartItem> findByOrder();
	
	CartItem updateCartItem(CartItem cartItem);
	
	void removeCartItem(CartItem cartItem);
	
	CartItem findById(Long id);
	
	CartItem save(CartItem cartItem);
	
}
