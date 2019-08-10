package com.huunghia.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huunghia.entity.Book;
import com.huunghia.entity.CartItem;
import com.huunghia.entity.ShoppingCart;
import com.huunghia.entity.User;
import com.huunghia.service.BookService;
import com.huunghia.service.CartItemService;
import com.huunghia.service.ShoppingCartService;
import com.huunghia.service.UserService;

@RestController
@RequestMapping("api/cart")
public class ShoppingCartController {

	@Autowired
	private UserService userService;

	@Autowired
	private BookService bookService;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private ShoppingCartService shoppingCartService;

	@PostMapping("add")
	public ResponseEntity<String> add(@RequestBody HashMap<String, String> mapper) {

		String bookId = mapper.get("bookId");
		String qty = mapper.get("qty");

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails) principal).getUsername();

		User user = userService.findByUsername(username);

		Book book = bookService.findOne(Long.parseLong(bookId));

		if (Integer.parseInt(qty) > book.getInStockNumber()) {
			return new ResponseEntity<String>("khong du hang", HttpStatus.BAD_REQUEST);
		}

		cartItemService.addBookToCartItem(book, user, Integer.parseInt(qty));

		return new ResponseEntity<String>("them moi book thanh cong", HttpStatus.CREATED);
	}

	@GetMapping("getCartItemList")
	public List<CartItem> getCartItemList() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails) principal).getUsername();
		User user = userService.findByUsername(username);

		ShoppingCart shoppingCart = user.getShoppingCart();

		List<CartItem> cartItems = cartItemService.findByShoppingCart(shoppingCart);

		shoppingCartService.updateShoppingCart(shoppingCart);

		return cartItems;

	}

	@GetMapping("getShoppingCart")
	public ShoppingCart getShoppingCart() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails) principal).getUsername();
		User user = userService.findByUsername(username);

		ShoppingCart shoppingCart = user.getShoppingCart();

		shoppingCartService.updateShoppingCart(shoppingCart);

		return shoppingCart;
	}

	@DeleteMapping("removeCartItem/{id}")
	public ResponseEntity<String> removeCartItem(@PathVariable String id) {
		cartItemService.removeCartItem(cartItemService.findById(Long.parseLong(id)));
		return new ResponseEntity<String>("xoa cartitem thanh cong", HttpStatus.OK);
	}

	@PostMapping("updateCartItem")
	public ResponseEntity<String> updateCartItem(@RequestBody HashMap<String, String> mapper) {
		String cartItemId = mapper.get("cartItemId");
		String qty = mapper.get("qty");

		CartItem cartItem = cartItemService.findById(Long.parseLong(cartItemId));
		cartItem.setQty(Integer.parseInt(qty));

		cartItemService.updateCartItem(cartItem);

		return new ResponseEntity<String>("cartItem update thanh cong", HttpStatus.OK);
	}

}
