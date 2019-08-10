package com.huunghia.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huunghia.entity.Book;
import com.huunghia.entity.BookToCartItem;
import com.huunghia.entity.CartItem;
import com.huunghia.entity.ShoppingCart;
import com.huunghia.entity.User;
import com.huunghia.repository.BookToCartItemRepository;
import com.huunghia.repository.CartItemRespository;
import com.huunghia.service.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService{

	@Autowired
	private CartItemRespository cartItemRespository;
	
	@Autowired
	private BookToCartItemRepository bookToCartItemRepository;
	
	
	@Override
	public CartItem addBookToCartItem(Book book, User user, int qty) {
		List<CartItem> cartItems = findByShoppingCart(user.getShoppingCart());
		
		for(CartItem cartItem : cartItems) {
			if(book.getId() == cartItem.getBook().getId()) {
				cartItem.setQty(cartItem.getQty() + qty);
				cartItem.setSubtotal(new BigDecimal(book.getOurPrice()).multiply(new BigDecimal(cartItem.getQty())));
				cartItemRespository.save(cartItem);
				return cartItem;
			}
		}
		
		CartItem cartItem = new CartItem();
		cartItem.setShoppingCart(user.getShoppingCart());
		cartItem.setBook(book);
		cartItem.setQty(qty);
		cartItem.setSubtotal(new BigDecimal(book.getOurPrice()).multiply(new BigDecimal(qty)));
		
		cartItem = cartItemRespository.save(cartItem);
		
		BookToCartItem bookToCartItem= new BookToCartItem();
		bookToCartItem.setBook(book);
		bookToCartItem.setCartItem(cartItem);
		
		bookToCartItemRepository.save(bookToCartItem);
		
		return cartItem;
	}

	@Override
	public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart) {
		return cartItemRespository.findByShoppingCart(shoppingCart);
	}

	@Override
	public List<CartItem> findByOrder() {
		return null;
	}

	@Override
	public CartItem updateCartItem(CartItem cartItem) {
		BigDecimal bigDecimal = new BigDecimal(cartItem.getBook().getOurPrice())
				.multiply(new BigDecimal(cartItem.getQty()));
		bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		
		cartItem.setSubtotal(bigDecimal);
		
		cartItemRespository.save(cartItem);
		
		return cartItem;
	}

	@Transactional
	@Override
	public void removeCartItem(CartItem cartItem) {
		bookToCartItemRepository.deleteByCartItem(cartItem);
		cartItemRespository.delete(cartItem);
	}

	@Override
	public CartItem findById(Long id) {
		return cartItemRespository.findById(id).get();
	}

	@Override
	public CartItem save(CartItem cartItem) {
		return cartItemRespository.save(cartItem);
	}

}
