package com.huunghia.service;

import java.util.Set;

import com.huunghia.entity.User;
import com.huunghia.entity.UserBilling;
import com.huunghia.entity.UserPayment;
import com.huunghia.entity.UserRole;
import com.huunghia.entity.UserShipping;

public interface UserService {

	User createUser(User user, Set<UserRole> userRoles);

	User findByUsername(String username);

	User findByEmail(String email);

	User findById(Long id);

	User save(User user);

	void updateUserPaymentInfo(UserBilling userBilling, UserPayment userPayment, User user);

	void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user);

	void setUserDefaultPayment(Long userPaymentId, User user);

	void updateUserShipping(UserShipping userShipping, User user);

	void setUserDefaultShipping(Long userShippingId, User user);

}
