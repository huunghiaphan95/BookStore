package com.huunghia.service;

import com.huunghia.entity.UserShipping;

public interface UserShippingService {
	UserShipping findById(Long id);
	
	void removeById(Long id);
}
