package com.example.demo.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.demo.model.Member;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	@Value("${jwt.secret}")
	private String secretKey;
	
	@Value("${jwt.expiration}")
	private long expirationTime;
	
	public String generateToken(Member member) {
		Map<String,Object> claims=new HashMap<>();
		// 在token裡添加額外訊息
		claims.put("id", member.getId());
		// claims.put("email",member.getEmail());
		return createToken(claims,member.getUsername());
	}
	
	private String createToken(Map<String,Object> claims,String subject) {
		return Jwts.builder()
				   .setClaims(claims)
				   .setSubject(subject)
				   .setIssuer("JWTTestSystem")
				   .setIssuedAt(new Date(System.currentTimeMillis()))
				   .setExpiration(new Date(System.currentTimeMillis()+1000*expirationTime))
				   .signWith(SignatureAlgorithm.HS256,secretKey)
				   .compact();
	}
	
	public Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}
	
	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}
	
	public boolean isTokenExpired(String token) {
		return extractAllClaims(token).getExpiration().before(new Date());
	}
	
	// 檢查username正確性跟token有沒有過期
	public boolean validateToken(String token,UserDetails userdetails) {
		final String extractedUsername=extractUsername(token);
		return (extractedUsername.equals(userdetails.getUsername()) && !isTokenExpired(token));
	}
}
