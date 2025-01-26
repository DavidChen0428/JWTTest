package com.example.demo.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Member;
import com.example.demo.model.jwt.CustomUserDetails;
import com.example.demo.util.JwtUtil;

@SpringBootTest
public class JwtGenerateTest {
	@Autowired
	JwtUtil jwtutil;
	
	@Test
	public void test(){
		System.out.printf("token generate success : %s\n",jwtutil.generateToken(new Member("david","1234","david@abc.com")));
		
		System.out.printf("validate token : %b\n",jwtutil.validateToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkYXZpZCIsImlzcyI6IkpXVFRlc3RTeXN0ZW0iLCJpYXQiOjE3Mzc4Nzc1NTAsImV4cCI6MTczODQ4MjM1MH0.Kf-tHp-0S0_OybUb-uhlj55PHCU4N-9XX5OeVRjixj8", new CustomUserDetails(new Member("david","1234","david@abc.com"))));
		
	}
}
