package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Member;

public interface MemberRepository extends JpaRepository<Member,Integer>{
	List<Member> findByUsername(String username);
	List<Member> findByUsernameAndPassword(String username,String password);
	List<Member> findById(int id);
}
