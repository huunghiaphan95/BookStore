package com.huunghia.service;

import com.huunghia.entity.BillingAddress;
import com.huunghia.entity.Order;
import com.huunghia.entity.Payment;
import com.huunghia.entity.ShippingAddress;
import com.huunghia.entity.ShoppingCart;
import com.huunghia.entity.User;

public interface OrderService {
	Order createOrder(
			ShoppingCart shoppingCart,
			ShippingAddress shippingAddress,
			BillingAddress billingAddress,
			Payment payment,
			String shippingMethod,
			User user
			);
	
	Order findOne(Long id);
}
