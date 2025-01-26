package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.ChatRepository;
import com.example.demo.dao.MemberRepository;
import com.example.demo.model.Chat;
import com.example.demo.model.Member;
import com.example.demo.service.impl.CustomUserDetailsService;
import com.example.demo.util.JwtUtil;

@RestController
public class ChatController {
	@Autowired
	private ChatRepository chatrepository;
	
	@Autowired
	private MemberRepository memberrepository;
	
	@Autowired
	private JwtUtil jwtutil;
	
	@Autowired
	private AuthenticationManager authenticationmanager;
	
	@Autowired
	private CustomUserDetailsService customuserdetailsservice;
	
	
	@PostMapping("addChat")
	// @RequestHeader("Authorization") String token -> 表示從 HTTP 請求的標頭中獲取授權 token。
	public String addChat(@RequestHeader("Authorization") String token, @RequestBody Chat chat) {
		String jwt = token.substring(7);// JWT擷取，去除前贅詞"Bearer "
        String username = jwtutil.extractUsername(jwt);
        UserDetails userDetails = customuserdetailsservice.loadUserByUsername(username);

        if (jwtutil.validateToken(jwt, userDetails)) {
            Member member = memberrepository.findByUsername(username).get(0);
            chat.setMember(member);
            chatrepository.save(chat);
            return "Chat added successfully";
        } else {
            return "Invalid token";
        }
	}
}
