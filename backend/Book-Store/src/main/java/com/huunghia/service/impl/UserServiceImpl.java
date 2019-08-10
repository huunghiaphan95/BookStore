package com.huunghia.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huunghia.entity.ShoppingCart;
import com.huunghia.entity.User;
import com.huunghia.entity.UserBilling;
import com.huunghia.entity.UserPayment;
import com.huunghia.entity.UserRole;
import com.huunghia.entity.UserShipping;
import com.huunghia.repository.RoleRepository;
import com.huunghia.repository.UserBillingRepository;
import com.huunghia.repository.UserPaymentRepository;
import com.huunghia.repository.UserRepository;
import com.huunghia.repository.UserShippingResponsity;
import com.huunghia.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserPaymentRepository userPaymentRepository;

	@Autowired
	private UserBillingRepository userBillingRepository;

	@Autowired
	private UserShippingResponsity userShippingRepository;

	@Transactional
	@Override
	public User createUser(User user, Set<UserRole> userRoles) {
		User localUser = userRepository.findByUsername(user.getUsername());

		if (localUser != null) {
			LOG.info("username {} da ton tai", user.getUsername());
		} else {
			for (UserRole ur : userRoles) {
				roleRepository.save(ur.getRole());
			}

			user.getUserRoles().addAll(userRoles);

			ShoppingCart shoppingCart = new ShoppingCart();
			shoppingCart.setUser(user);
			user.setShoppingCart(shoppingCart);

			user.setUserPayments(new ArrayList<UserPayment>());
			user.setUserShippings(new ArrayList<UserShipping>());

			localUser = userRepository.save(user);
		}
		return localUser;
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User findById(Long id) {
		return userRepository.findById(id).get();
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public void updateUserPaymentInfo(UserBilling userBilling, UserPayment userPayment, User user) {
		save(user);
		userBillingRepository.save(userBilling);
		userPaymentRepository.save(userPayment);
	}

	@Override
	public void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user) {
		userPayment.setUser(user);
		userPayment.setUserBilling(userBilling);
		userPayment.setDefaultPayment(true);
		userBilling.setUserPayment(userPayment);
		user.getUserPayments().add(userPayment);
		save(user);
	}

	@Override
	public void setUserDefaultPayment(Long userPaymentId, User user) {
		List<UserPayment> userPayments = userPaymentRepository.findAll();

		for (UserPayment userPayment : userPayments) {
			if (userPayment.getId() == userPaymentId) {
				userPayment.setDefaultPayment(true);
				userPaymentRepository.save(userPayment);
			} else {
				userPayment.setDefaultPayment(false);
				userPaymentRepository.save(userPayment);
			}
		}
	}

	@Override
	public void setUserDefaultShipping(Long userShippingId, User user) {
		List<UserShipping> userShippings = userShippingRepository.findAll();

		for (UserShipping userShipping : userShippings) {
			if (userShipping.getId() == userShippingId) {
				userShipping.setDefaultShipping(true);
				userShippingRepository.save(userShipping);
			} else {
				userShipping.setDefaultShipping(false);
				userShippingRepository.save(userShipping);
			}
		}
	}

	@Override
	public void updateUserShipping(UserShipping userShipping, User user) {
		userShipping.setUser(user);
		userShipping.setDefaultShipping(true);
		user.getUserShippings().add(userShipping);
		save(user);

	}

}
