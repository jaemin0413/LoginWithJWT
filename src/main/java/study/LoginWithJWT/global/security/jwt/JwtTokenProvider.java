package study.LoginWithJWT.global.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import study.LoginWithJWT.global.apiPayload.code.ErrorCode;
import study.LoginWithJWT.global.apiPayload.exception.AuthException;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    // jwt 생성, 검증, 파싱하는 provider

    @Value("${spring.jwt.secret}")
    private String secret;
    @Value("${spring.jwt.access-token-validity}")
    private long accessTokenValidity;
    @Value("${spring.jwt.refresh-token-validity}")
    private long refreshTokenValidity;

    private JwtParser jwtParser;
    private Key secretKey;
    // 위의 둘은 생성자가 아니라 @PostConstruct에서 초기화되기 떄문에 final을 쓸 수 없ㅎ음
    // 자바에서 final이 붙은 필드는 객체 생성 시점에 반드시 값이 할당되어야 함
    // 하지만 위의 두 필드는 @PostConstruct가 선언되었음을 확인하고 해당 메서드에서 초기화시킴

    @PostConstruct
    public void init() {
        this.secretKey= Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret) );
        //base64로 인코딩된 비밀키 가져온 다음 암호화
        this.jwtParser = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build();
    }

    public String createdAccessToken(String email) {
        try {
            return generateToken(email, accessTokenValidity, "access");
        } catch (JwtException e) {
            throw new AuthException(ErrorCode.JWT_GENERATION_FAILED);
        }
    }

    public String createdRefreshToken(String email) {
        try {
            return generateToken(email, refreshTokenValidity, "refresh");
        } catch (JwtException e) {
            throw new AuthException(ErrorCode.JWT_GENERATION_FAILED);
        }
    }

    // jwt 토큰 생성, 검증, 인증 객체를 만들어 반환
    private String generateToken(String email, long validity,String category) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + validity);

        return Jwts.builder()
                .setSubject(email)
                .claim("category", category) //access인지 refresh인지
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(getSigningKey(),SignatureAlgorithm.HS512)
                .compact();
    }

    // jwt 토큰 유효성 검증, 파싱 성공시 유효, 아니라면 예외 반환
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new AuthException(ErrorCode.JWT_EXPIRED_TOKEN);
        } catch (JwtException e) {
            throw new AuthException(ErrorCode.JWT_INVALID_TOKEN);
        }
    }




    // jwt의 payload(claims 객체) 추출
    public Claims parseClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public String extractEmail(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }


    // jwt 발급시 필요한 시크릿 키를 key 객체로 변환
    private Key getSigningKey() {
        return this.secretKey;
    }
    public boolean isExpired(String token) {
        try {
            Claims claims = parseClaims(token);
            return claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public boolean isValid(String token) {
        try {
            validateToken(token);
            return true;
        }
        catch (AuthException e) {
            return false;
        }
    }

}
