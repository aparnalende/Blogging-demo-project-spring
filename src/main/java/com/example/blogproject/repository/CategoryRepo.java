package com.example.blogproject.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.blogproject.entities.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
