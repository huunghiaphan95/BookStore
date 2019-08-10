package com.huunghia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huunghia.entity.UserShipping;
import com.huunghia.repository.UserShippingResponsity;
import com.huunghia.service.UserShippingService;

@Service
public class UserShippingServiceImpl implements UserShippingService{

	@Autowired
	private UserShippingResponsity userShippingRepository;
	
	
	@Override
	public UserShipping findById(Long id) {
		return userShippingRepository.findById(id).get();
	}

	@Override
	public void removeById(Long id) {
		userShippingRepository.deleteById(id);
		
	}

}
