package com.example.demo.model.jwt;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.model.Member;
// 建立類別實作UserDetails
// Spring Security 在認證和授權過程中會使用這個介面來檢索和驗證使用者的信息。
// 通過實作 UserDetails，你向 Spring Security 提供了必要的使用者詳細信息，以便有效地管理認證和授權。
public class CustomUserDetails implements UserDetails{
	private Member member;
	
	public CustomUserDetails(Member member) {
		super();
		this.member = member;
	}

	@Override
	// 返回授予使用者的權限（角色/許可權）
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
	}

	@Override
	// 返回用於驗證使用者的密碼。
	public String getPassword() {
		return member.getPassword();
	}

	@Override
	// 返回用於驗證使用者的使用者名稱。
	public String getUsername() {
		return member.getUsername();
	}

	@Override
	// 指示使用者的帳戶是否過期。 true -> 未過期可使用
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	// 指示使用者是否被鎖定。 true -> 未被鎖定
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	// 指示使用者的憑證（密碼）是否過期。 true -> 憑證有效且未過期
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	// 指示使用者是否啟用。 true -> 使用者已啟用
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	

}
