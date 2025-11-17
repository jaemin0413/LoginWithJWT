package study.LoginWithJWT.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import study.LoginWithJWT.domain.user.dto.request.LoginRequest;
import study.LoginWithJWT.domain.user.dto.request.RegisterRequest;
import study.LoginWithJWT.domain.user.dto.response.TokenResponse;
import study.LoginWithJWT.domain.user.service.AuthService;
import study.LoginWithJWT.global.apiPayload.response.Response;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Response<TokenResponse>> register(@RequestBody RegisterRequest request) {
        TokenResponse response = authService.register(request);
        return ResponseEntity.ok(Response.ok(response));
    }

    @PostMapping("/login")
    public ResponseEntity<Response<TokenResponse>> login(@RequestBody LoginRequest request) {
        TokenResponse response = authService.login(request);
        return ResponseEntity.ok(Response.ok(response));
    }

    @GetMapping("/me")
    public ResponseEntity<Response<Map<String, String>>> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        Map<String, String> userInfo = new HashMap<>();
        if (authentication != null && authentication.isAuthenticated()) {
            userInfo.put("email", authentication.getPrincipal().toString());
            userInfo.put("authenticated", "true");
        } else {
            userInfo.put("authenticated", "false");
        }
        
        return ResponseEntity.ok(Response.ok(userInfo));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Response<TokenResponse>> refresh(@RequestHeader("Authorization") String refreshToken) {
        String token = refreshToken.replace("Bearer ", "");
        TokenResponse response = authService.refresh(token);
        return ResponseEntity.ok(Response.ok(response));
    }
}
