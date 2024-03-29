package com.huunghia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huunghia.entity.UserPayment;

@Repository
public interface UserPaymentRepository extends JpaRepository<UserPayment, Long>{

}
