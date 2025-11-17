package study.LoginWithJWT.global.security.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    // jwt 인증 성공 후 인증된 사용자의 정보를 담는 객체, 인증 결과물
    private final String email;

    public JwtAuthenticationToken(String email, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.email = email;
        setAuthenticated(true); // 토큰이 검증되었다고 명시
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return email; // 인증된 사용자를 email을 통해 누구인지 알게 함
    }
}
