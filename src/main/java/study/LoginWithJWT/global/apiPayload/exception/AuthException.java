package study.LoginWithJWT.global.apiPayload.exception;

import lombok.Getter;
import study.LoginWithJWT.global.apiPayload.code.ErrorCode;
import study.LoginWithJWT.global.apiPayload.response.Response;

@Getter
public class AuthException extends RuntimeException {

    private final ErrorCode errorCode;

    public AuthException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public Response<Void> toResponse() {
        return Response.fail(errorCode);
    }
}
