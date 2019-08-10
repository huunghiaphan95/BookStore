package com.huunghia.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.huunghia.entity.Role;
import com.huunghia.entity.User;
import com.huunghia.entity.UserRole;
import com.huunghia.security.SecurityUtility;
import com.huunghia.service.UserService;
import com.huunghia.utility.MailConstructor;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private MailConstructor mailConstructor;

	@Autowired
	private JavaMailSender mailSender;

	@PostMapping("user/newUser")
	public ResponseEntity<String> addNewUser(@RequestBody HashMap<String, String> mapper) {
		String username = mapper.get("username");
		String email = mapper.get("email");

		if (userService.findByUsername(username) != null) {
			return new ResponseEntity<String>("usernameExists", HttpStatus.BAD_REQUEST);
		}

		if (userService.findByEmail(email) != null) {
			return new ResponseEntity<String>("emailExists", HttpStatus.BAD_REQUEST);
		}

		User user = new User();
		user.setUsername(username);
		user.setEmail(email);

		String password = SecurityUtility.randomPassword();

		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);

		user.setPassword(encryptedPassword);

		Role role = new Role();
		role.setRoleId(1);
		role.setName("ROLE_USER");

		Set<UserRole> userRoles = new HashSet<UserRole>();
		userRoles.add(new UserRole(user, role));

		userService.createUser(user, userRoles);

		//gui password vao email cua user dang ki
		SimpleMailMessage emailMessage = mailConstructor.constructNewUserEmail(user, password);
		mailSender.send(emailMessage);

		return new ResponseEntity<String>("them moi user thanh cong", HttpStatus.CREATED);

	}

	@PostMapping("user/forgetPassword")
	public ResponseEntity<String> forgetPassword(@RequestBody HashMap<String, String> mapper) {
		User user = userService.findByEmail(mapper.get("email"));

		if (user == null) {
			return new ResponseEntity<String>("email khong ton tai", HttpStatus.BAD_REQUEST);
		}

		String password = SecurityUtility.randomPassword();

		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);

		user.setPassword(encryptedPassword);
		userService.save(user);

		//gui password vao email cua user dang ki
		SimpleMailMessage emailMessage = mailConstructor.constructNewUserEmail(user, password);
		mailSender.send(emailMessage);

		return new ResponseEntity<String>("email da duoc gui", HttpStatus.OK);

	}

	@PutMapping("user/updateUserInfo")
	public ResponseEntity<String> updateUser(@RequestBody HashMap<String, Object> mapper) throws Exception {
		int id = (Integer) mapper.get("id");
		String firstName = (String) mapper.get("firstName");
		String lastName = (String) mapper.get("lastName");
		String username = (String) mapper.get("username");
		String email = (String) mapper.get("email");
		String currentPassword = (String) mapper.get("currentPassword");
		String newPassword = (String) mapper.get("newPassword");

		User user = userService.findById(Long.valueOf(id));

		if (user == null)
			throw new Exception("user khong ton tai");

		BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();

		String dbUserPassword = user.getPassword(); // password hien tai luu vao db

		if (currentPassword != null) {
			// kiem tra currentPassword va dbUserPassword co trung nhau khong?
			if (passwordEncoder.matches(currentPassword, dbUserPassword)) {

				// newPassword khac rong
				if (!newPassword.isEmpty() && newPassword != null && !newPassword.equals("")) {
					// luu newPassword vao db
					user.setPassword(passwordEncoder.encode(newPassword));
				}

				user.setEmail(email);
			} else {
				return new ResponseEntity<String>("mat khau hien tai khong dung", HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<String>("mat khau hien tai khong duoc de trong", HttpStatus.BAD_REQUEST);
		}

		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUsername(username);

		userService.save(user);

		return new ResponseEntity<String>("cap nhat user thanh cong", HttpStatus.OK);

	}

	@GetMapping("getCurrentUser")
	public User getCurrentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		// tra ve username đa dung trong qua trinh xac thuc
		String username = ((UserDetails) principal).getUsername();
		
		User user = new User();

		if (username != null) {
			user = userService.findByUsername(username);
		}

		return user;
	}

}
