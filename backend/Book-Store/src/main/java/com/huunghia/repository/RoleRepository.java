package com.huunghia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huunghia.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

	
}
