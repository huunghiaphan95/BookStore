package com.huunghia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huunghia.entity.Order;
import com.huunghia.entity.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
	
	List<Order> findByUser(User user);

}
