package com.huunghia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huunghia.entity.CartItem;
import com.huunghia.entity.ShoppingCart;

@Repository
public interface CartItemRespository extends JpaRepository<CartItem, Long>{

	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
	
	//List<CartItem> findByOrder();
}
