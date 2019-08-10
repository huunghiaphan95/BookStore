package com.huunghia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huunghia.entity.UserBilling;

@Repository
public interface UserBillingRepository extends JpaRepository<UserBilling, Long>{

}
