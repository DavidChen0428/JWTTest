package com.example.demo.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.interceptor.JwtInterceptor;
import com.example.demo.service.impl.CustomUserDetailsService;

@Configuration
// 配置了認證管理器和提供者，用於實際執行用戶憑證驗證的邏輯。
// 負責配置Spring Security中的JWT攔截器和認證管理器，確保每個請求都會通過JWT驗證，並根據自定義的用戶詳細信息服務進行用戶認證。
public class SecurityConfig implements WebMvcConfigurer{
	@Autowired
	private JwtInterceptor jwtinterceptor;
	
	@Autowired
	private CustomUserDetailsService customuserdetailsservice;

	@Override
	// 向 Spring MVC 添加攔截器interceptor
	public void addInterceptors(InterceptorRegistry registry) {
		// 對指定路徑下做JWT驗證
		registry.addInterceptor(jwtinterceptor).addPathPatterns("/**");
	}
	
	@Bean 
	// 配置認證管理器
	// AuthenticationManager -> 處理身份驗證的核心接口。它會接收用戶的認證請求（例如用戶名和密碼），並驗證這些憑證是否正確。
	// 							用於管理和調用多個AuthenticationProvider來進行認證。
	AuthenticationManager authenticationManagerBean() throws Exception{
		// ProviderManager是AuthenticateManager的實作 -> 它內部維護了一個AuthenticationProvider列表
		//                                               當有認證請求進來時，它會逐一調用這些提供者來進行驗證。
		// 將daoAuthenticationProvider設為唯一的AuthenticationProvider
		// 創建了一個包含單個AuthenticationProvider（即daoAuthenticationProvider）的列表。這個列表會被傳遞給ProviderManager進行管理。
		return new ProviderManager(Collections.singletonList(daoAuthenticationProvider()));
	}
	
	@Bean
	// 配置認證提供者
	// DaoAuthenticationProvider：這是一個內置的Spring Security提供者，用於根據從UserDetailsService中加載的用戶詳細信息進行驗證。
	public DaoAuthenticationProvider daoAuthenticationProvider(){
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		provider.setUserDetailsService(customuserdetailsservice);// 設置自定義的UserDetailsService，它負責從數據源（如數據庫）中加載用戶詳細信息。
		return provider;
	}
}
