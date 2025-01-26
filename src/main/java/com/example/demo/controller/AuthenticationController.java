package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.MemberRepository;
import com.example.demo.model.jwt.AuthenticationRequest;
import com.example.demo.model.jwt.AuthenticationResponse;
import com.example.demo.service.impl.CustomUserDetailsService;
import com.example.demo.util.JwtUtil;

@RestController
// 處理用戶登入請求，生成JWT並返回給客戶端。
public class AuthenticationController {
	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customuserdetailsservice;
    
    @Autowired
    private MemberRepository memberrepository;

    @PostMapping("/login")
    // 自定義類別AuthenticationRequest(DTO??) -> username(String), password(String)
    // 自定義類別AuthenticationResponse(DTO??) -> jwt(String)
    // 返回型態為json格式，應該說只要有@RestController就會自動回傳成json型態
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
        	// 使用AuthenticationManager來驗證用戶名和密碼
            authenticationManager.authenticate(
            		// UsernamePasswordAuthenticationToken為Authentication的實作 -> 用於表示包含用戶名和密碼的身份驗證請求
            		// 													            提供了多種方法來處理用戶名和密碼的認證信息。
            		// 																主要作用是在身份驗證過程中，封裝用戶名和密碼這類憑證信息，並將其傳遞給 AuthenticationManager 進行驗證。
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        // 加載用戶
        final UserDetails customuserdetails = customuserdetailsservice.loadUserByUsername(authenticationRequest.getUsername());
        // 從用戶中得知username，然後產生token
        final String jwt = jwtUtil.generateToken(memberrepository.findByUsername(customuserdetails.getUsername()).get(0));

        // 返回響應
        // 生成並返回包含JWT的響應：最終，將生成的JWT封裝在 AuthenticationResponse 對象中，並以HTTP狀態碼200（OK）返回給客戶端。
        // ResponseEntity -> 用於構建 HTTP 回應的類
        // 					 能夠攜帶回應的狀態碼、頭信息以及回應體，因此比單純返回一個字符串或對象更為靈活和強大。
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
