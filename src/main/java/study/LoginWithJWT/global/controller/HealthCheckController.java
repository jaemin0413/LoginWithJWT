package study.LoginWithJWT.global.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.LoginWithJWT.global.apiPayload.response.Response;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HealthCheckController {

    @GetMapping("/health")
    public ResponseEntity<Response<Map<String, Object>>> healthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now());
        health.put("service", "LoginWithJWT");
        
        return ResponseEntity.ok(Response.ok(health));
    }

    @GetMapping("/info")
    public ResponseEntity<Response<Map<String, String>>> info() {
        Map<String, String> info = new HashMap<>();
        info.put("application", "LoginWithJWT");
        info.put("version", "1.0.0");
        info.put("description", "JWT 기반 로그인 연습 프로젝트");
        
        return ResponseEntity.ok(Response.ok(info));
    }
}
