package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dao.MemberRepository;
import com.example.demo.model.jwt.CustomUserDetails;
import com.example.demo.model.Member;
@Service
// UserDetailsService 專注於從資料來源（如資料庫）加載使用者詳細資料(Autowired UserDetails)
// UserDetails 則專注於提供使用者詳細資料的結構
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	private MemberRepository memberrepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<Member> list=memberrepository.findByUsername(username);
		if(list.size()==0) {
			throw new UsernameNotFoundException("user not found");
		}
		
		return new CustomUserDetails(list.get(0));
	}

}
