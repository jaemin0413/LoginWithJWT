package study.LoginWithJWT.global.security.handler;

import lombok.Getter;
import study.LoginWithJWT.global.apiPayload.code.ErrorCode;
import study.LoginWithJWT.global.apiPayload.response.BaseResponse;

@Getter
public class AuthFailureHandler extends RuntimeException{
    private final ErrorCode errorCode;

    public AuthFailureHandler(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BaseResponse<String> toResponse() {
        return new BaseResponse<>(false, errorCode.getCode(), errorCode.getMessage());
    }
}
