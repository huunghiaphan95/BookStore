package com.huunghia.controller;

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

import com.huunghia.entity.User;
import com.huunghia.entity.UserShipping;
import com.huunghia.service.UserService;
import com.huunghia.service.UserShippingService;

@RestController
@RequestMapping("api/shipping")
public class ShippingController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserShippingService userShippingService;
	
	@PostMapping("add")
	public ResponseEntity<String> addShipping(@RequestBody UserShipping userShipping){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails) principal).getUsername();
		
		User user = userService.findByUsername(username);
		
		userService.updateUserShipping(userShipping, user);
		
		return new ResponseEntity<String>("them moi userShipping thanh cong", HttpStatus.CREATED);
	}
	
	@GetMapping("/getUserShippingList")
	public List<UserShipping> getUserShippingList(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails) principal).getUsername();
		
		User user = userService.findByUsername(username);
		
		return user.getUserShippings();
	}
	
	@DeleteMapping("remove/{id}")
	public ResponseEntity<String> remove(@PathVariable String id){
		userShippingService.removeById(Long.parseLong(id));
		return new ResponseEntity<String>("Shipping xoa thanh cong", HttpStatus.OK);
	}
	
	@PostMapping("setDefault")
	public ResponseEntity<String> setDefault(@RequestBody String id){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails) principal).getUsername();
		
		User user = userService.findByUsername(username);
		
		userService.setUserDefaultShipping(Long.parseLong(id), user);
		
		return new ResponseEntity<String>("setDefault Shipping thanh cong", HttpStatus.OK);
	}
}
