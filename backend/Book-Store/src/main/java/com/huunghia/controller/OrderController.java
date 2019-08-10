package com.huunghia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huunghia.entity.Order;
import com.huunghia.entity.User;
import com.huunghia.service.UserService;

@RestController
@RequestMapping("api/order")
public class OrderController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("getOrderList")
	public List<Order> getOrderList(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// tra ve username đa dung trong qua trinh xac thuc
		String username = ((UserDetails) principal).getUsername();
		User user = userService.findByUsername(username);
		return user.getOrders();
	}

}
