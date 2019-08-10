package com.huunghia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huunghia.entity.UserShipping;

@Repository
public interface UserShippingResponsity extends JpaRepository<UserShipping, Long>{

}
