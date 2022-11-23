package com.example.blogproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.blogproject.repository.UserRepo;

@SpringBootTest
class BlogProjectApplicationTests {

	@Autowired
	private UserRepo userRepo;
	
	
	@Test
	void contextLoads() {
	}

	@Test
	public void repoTest() {
	String className=this.userRepo.getClass().getName();
	String pckgName=this.userRepo.getClass().getPackageName();
	System.out.println(className);
	System.out.println(pckgName);
	}
	
	
}
