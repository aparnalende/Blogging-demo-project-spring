package com.example.blogproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blogproject.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

}
