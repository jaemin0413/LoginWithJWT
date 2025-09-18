package study.LoginWithJWT.global.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String secret;
    private Long accessTokenValidity;

    private Long refreshTokenValidity;

    public String getSecret() {
        return secret;
    }
    public Long getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public Long getRefreshTokenValidity() {
        return refreshTokenValidity;
    }
}
