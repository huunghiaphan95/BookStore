package com.huunghia.service;

import com.huunghia.entity.UserPayment;

public interface UserPaymentService {
	UserPayment findById(Long id);
	void removeById(Long id);
}
