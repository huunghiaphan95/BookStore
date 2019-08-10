package com.huunghia.utility;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.huunghia.entity.User;

@Component
public class MailConstructor {

	public SimpleMailMessage constructNewUserEmail(User user, String password) {
		String message = "Username: " + user.getUsername() + "\nPassword: " + password;
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setSubject("BookStore - New User");
		email.setText(message);
		email.setFrom("nghia1412468@gmail.com");

		return email;
	}

}
