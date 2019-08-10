package com.huunghia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huunghia.entity.BookToCartItem;
import com.huunghia.entity.CartItem;

@Repository
public interface BookToCartItemRepository extends JpaRepository<BookToCartItem, Long>{
	void deleteByCartItem(CartItem cartItem);

}
