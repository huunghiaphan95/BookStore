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
import com.huunghia.entity.UserBilling;
import com.huunghia.entity.UserPayment;
import com.huunghia.service.UserPaymentService;
import com.huunghia.service.UserService;

@RestController
@RequestMapping("api/payment")
public class PaymentController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserPaymentService userPaymentService;

	@PostMapping("add")
	public ResponseEntity<String> addNewCreditCard(@RequestBody UserPayment userPayment) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// tra ve username đa dung trong qua trinh xac thuc
		String username = ((UserDetails) principal).getUsername();

		User user = userService.findByUsername(username);

		UserBilling userBilling = userPayment.getUserBilling();

		userService.updateUserBilling(userBilling, userPayment, user);

		return new ResponseEntity<String>("payment them moi (cap nhat) thanh cong", HttpStatus.OK);
	}

	@PostMapping("setDefault")
	public ResponseEntity<String> setDefault(@RequestBody String id) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// tra ve username đa dung trong qua trinh xac thuc
		String username = ((UserDetails) principal).getUsername();

		User user = userService.findByUsername(username);

		userService.setUserDefaultPayment(Long.valueOf(id), user);

		return new ResponseEntity<String>("payment setDefault thanh cong", HttpStatus.OK);
	}

	@DeleteMapping("remove/{id}")
	public ResponseEntity<String> remove(@PathVariable String id) {

		userPaymentService.removeById(Long.valueOf(id));

		return new ResponseEntity<String>("payment xoa thanh cong", HttpStatus.OK);
	}
	
	@GetMapping("getUserPaymentList")
	public List<UserPayment> getUserPaymentList(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// tra ve username đa dung trong qua trinh xac thuc
		String username = ((UserDetails) principal).getUsername();

		User user = userService.findByUsername(username);
		
		return user.getUserPayments();
	}

}
