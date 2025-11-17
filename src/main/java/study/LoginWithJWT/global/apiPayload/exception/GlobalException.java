package study.LoginWithJWT.global.apiPayload.exception;

import org.springframework.http.HttpStatus;
import study.LoginWithJWT.global.apiPayload.code.BaseCode;
import study.LoginWithJWT.global.apiPayload.code.ErrorCode;

public class GlobalException extends RuntimeException {

    private final ErrorCode errorCode;

    public GlobalException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }


    public BaseCode getCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return errorCode.getStatus();
    }

    public String getErrorCode() {
        return errorCode.getCode();
    }

    public String getErrorMessage() {
        return errorCode.getMessage();
    }

}
