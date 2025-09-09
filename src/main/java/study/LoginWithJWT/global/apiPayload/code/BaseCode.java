package study.LoginWithJWT.global.apiPayload.code;

import org.springframework.http.HttpStatus;

public interface BaseCode {
    HttpStatus getStatus();
    String getCode();
    String getMessage();

    // API 응닫ㅂ 상태 코드들을 일관된 형식으로 만들기 위한 규칙

}
