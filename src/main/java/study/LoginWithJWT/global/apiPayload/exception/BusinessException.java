package study.LoginWithJWT.global.apiPayload.exception;

import org.springframework.http.HttpStatus;
import study.LoginWithJWT.global.apiPayload.code.BaseCode;
import study.LoginWithJWT.global.apiPayload.code.ErrorCode;

public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        // RuntimeException의 객체를 먼저 만듦
        // 이는 RuntimeException이 가지고 있는 세 가지 기능을 상속받기 위함
        // 1. 위의 생성자를 통해 전달받은 에러 메세지 문자열을 객체 내부에 저장
        // 2. 코드가 실행되는 순간 부모인 RuntimeException의 생성자가 호출되며 그 시점의 모든 메서드 호출 기록을 객체 내부에 저장
        // 3. Exception을 직접 상속받는 것보다 깔끔한 코드 구현 가능
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

    // 이렇게 반환받은 값들은 @ExceptionHandler에서 사용함

}
