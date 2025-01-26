package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Member;

public interface MemberService {
	// create
	void addMember(Member member);
	
	// read
	List<Member> allMember();
	Member login(String username,String password);
	boolean isUsernameBeenUsed(String username);
	// update
	void updateMember(Member member);
	
	// delete
	void deleteMemberById(int id);
	
}
