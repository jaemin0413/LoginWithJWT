package study.LoginWithJWT.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class AuthResponse {

    @Getter
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class JoinResultRes{
        private String name;
        private String email;
        private String accessToken;
        private String refreshToken;
    }

}
