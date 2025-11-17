package study.LoginWithJWT.domain.user.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class AuthRequest {

    public static class SignUpReq {
        @NotNull
        private String name;
        @NotNull
        private String email;
        @NotNull
        private String loginId;
        @NotNull
        private String password;
        @NotNull
        private String confirmPassword;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class LogInReq {
        @NotNull
        private String loginId;

        @NotNull
        private String email;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class FindPasswordReq {
        @NotNull
        private String loginId;
        @NotNull
        private String name;
        @NotNull
        private String eamil;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CheckCurrentPasswordReq {

        @NotNull
        private String currentPassword;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class RecoverPassWordReq {

        @NotNull
        private String newPassWord;

        @NotNull
        private String confirmPassWord;
    }

}
