package com.huunghia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huunghia.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
