package com.example.demo.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.service.impl.CustomUserDetailsService;
import com.example.demo.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
// 處理JWT驗證，選擇Interceptor
// Interceptor是一個應用程序層面的攔截和處理機制；JwtUtil.validateJwt()是一個專門用來驗證JWT的工具方法
// 攔截請求並驗證JWT -> 攔截請求並檢查JWT的存在性及有效性
// 在請求中添加JWT -> interceptor可以在請求頭中添加JWT，以確保服務器能夠識別並授權該請求。
//					 通常用於用戶已經登入且JWT存儲在客戶端（例如瀏覽器的本地存儲或cookie）中的情況。
// 在請求中添加JWT -> 刷新Token，保持用戶的登入狀態有效。
// 在 Spring MVC 層面上進行處理，更加適合需要與 Spring MVC 生命週期緊密結合的操作。
// Interceptor 由 Spring 管理，較容易與其他 Spring 組件整合。
public class JwtInterceptor implements HandlerInterceptor{
	@Autowired
    private JwtUtil jwtutil;

    @Autowired 
    CustomUserDetailsService customuserdetailsservice;

    @Override
    // 在每個請求被處理前調用該方法
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String authorizationHeader = request.getHeader("Authorization");// 從請求頭取得Authorization欄位

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {// 如果Authorization欄位是Bearer開頭
            jwt = authorizationHeader.substring(7); // 提取JWT
            username = jwtutil.extractUsername(jwt); // 提取Username
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {// 如果用戶名存在且當前上下文中未設置認證
            UserDetails userDetails = this.customuserdetailsservice.loadUserByUsername(username); // 載入用戶資料

            if (jwtutil.validateToken(jwt, userDetails)) {// 驗證JWT是否有效
            	// 設置認證 -> 從JWT中提取的信息來設置應用程序的認證上下文，目的是讓後續的業務邏輯和控制器能夠方便地訪問當前用戶的身份信息和權限，從而做出正確的業務決策。
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 使用SecurityContextHolder保存當前用戶的認證信息
                // 應用程序的其他部分就能夠使用SecurityContextHolder來獲取當前用戶的身份和權限，從而對請求進行授權處理。
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        return true;// 表示請求可以繼續進行
    }
}
