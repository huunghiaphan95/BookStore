package com.huunghia.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huunghia.entity.BillingAddress;
import com.huunghia.entity.Order;
import com.huunghia.entity.Payment;
import com.huunghia.entity.ShippingAddress;
import com.huunghia.entity.ShoppingCart;
import com.huunghia.entity.User;
import com.huunghia.service.OrderService;
import com.huunghia.service.ShoppingCartService;
import com.huunghia.service.UserService;

@RestController
@RequestMapping("api/checkout")
public class CheckoutController {

	@Autowired
	private UserService userService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private ShoppingCartService shoppingCartService;

	@PostMapping("checkout")
	public Order checkout(@RequestBody HashMap<String, Object> mapper) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// tra ve username da dung trong qua trinh xac thuc
		String username = ((UserDetails) principal).getUsername();
		User user = userService.findByUsername(username);

		ObjectMapper om = new ObjectMapper();

		//chuyen cac doi tuong gui len tu request thanh cac Object tuong ung
		ShippingAddress shippingAddress = om.convertValue(mapper.get("shippingAddress"), ShippingAddress.class);
		BillingAddress billingAddress = om.convertValue(mapper.get("billingAddress"), BillingAddress.class);
		Payment payment = om.convertValue(mapper.get("payment"), Payment.class);
		String shippingMethod = (String) mapper.get("shippingMethod");

		ShoppingCart shoppingCart = user.getShoppingCart();

		Order order = orderService.createOrder(shoppingCart, shippingAddress, billingAddress, payment, shippingMethod,
				user);

		//sau khi checkout, clear shopping cart hien tai
		shoppingCartService.clearShoppingCart(shoppingCart);

		return order;
	}

}
