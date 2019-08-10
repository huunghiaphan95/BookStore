package com.huunghia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huunghia.entity.UserPayment;
import com.huunghia.repository.UserPaymentRepository;
import com.huunghia.service.UserPaymentService;

@Service
public class UserPaymentServiceImpl implements UserPaymentService{

	@Autowired
	private UserPaymentRepository userPaymentRepository;
	
	@Override
	public UserPayment findById(Long id) {
		return userPaymentRepository.findById(id).get();
	}

	@Override
	public void removeById(Long id) {
		userPaymentRepository.deleteById(id);
	}

}
