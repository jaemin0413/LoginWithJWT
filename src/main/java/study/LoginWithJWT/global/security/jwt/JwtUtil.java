package study.LoginWithJWT.global.security.jwt;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;


    public JwtUtil(JwtTokenProvider jwtTokenProvider, JwtProperties jwtProperties) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtProperties = jwtProperties;
    }

    public String extractCategory(String token) {
        return jwtTokenProvider.parseClaims(token).get("Category", String.class);
    }

    public long getRemaingSeconds(String token) {
        Date expiration = jwtTokenProvider.parseClaims(token).getExpiration();
        return expiration.getTime() / 1000;
    }

    public int getAccessTokenValidity(String token) {
        return (int)(jwtProperties.getAccessTokenValidity()/1000);
    }
    public int getRefreshTokenValidity(String token) {
        return (int)(jwtProperties.getRefreshTokenValidity()/1000);
    }

    public Long getUserIdFromToken(String token) {
        try {
            Claims claims = jwtTokenProvider.parseClaims(token);
            return claims.get("userId", Long.class);
        }catch ( Exception e ) {
            log.warn("jwt userid 추출 실패",e);
            return null;
        }
    }


}
