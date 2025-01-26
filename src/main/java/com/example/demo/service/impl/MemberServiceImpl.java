package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.MemberRepository;
import com.example.demo.model.Member;
import com.example.demo.service.MemberService;
@Service
public class MemberServiceImpl implements MemberService{
	@Autowired
	MemberRepository memberrepository;
	
	@Override
	public void addMember(Member member) {
		if(isUsernameBeenUsed(member.getUsername())) {
			System.out.printf("Username been used: %s\n",member.getUsername());
		}else {
			memberrepository.save(member);
		}
	}

	@Override
	public List<Member> allMember() {
		return memberrepository.findAll();
	}

	@Override
	public Member login(String username, String password) {
		Member member=null;
		List<Member> list=memberrepository.findByUsernameAndPassword(username, password);
		if(list.size()!=0) {
			member=list.get(0);
		}
		return member;
	}

	@Override
	public void updateMember(Member member) {
		memberrepository.save(member);
	}

	@Override
	public void deleteMemberById(int id) {
		memberrepository.deleteById(id);
	}

	@Override
	public boolean isUsernameBeenUsed(String username) {
		boolean usernameBeenUsed=false;
		if(memberrepository.findByUsername(username).size()!=0) {
			usernameBeenUsed=true;
		}
		return usernameBeenUsed;
	}

}
