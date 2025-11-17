package study.LoginWithJWT.global.apiPayload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import study.LoginWithJWT.global.apiPayload.code.BaseCode;
import study.LoginWithJWT.global.apiPayload.code.ResultCode;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"status", "code", "message", "data"})
public class Response<T> {

    private final HttpStatus status;
    private final String code;
    private final String message;

    private final T data;

    public static Response<Void> ok() {
        return new Response<>(ResultCode.OK.getStatus(),ResultCode.OK.getCode(),ResultCode.OK.getMessage(),null);
    };

    public static <T> Response<T> ok(T data) {
        return new Response<>(ResultCode.OK.getStatus(),ResultCode.OK.getCode(),ResultCode.OK.getMessage(),data);
    }

    // 일반적인 성공 케이스

    public static <T> Response<T> of(BaseCode code, T data) {
        return new Response<>(code.getStatus(),code.getCode(),code.getMessage(),data);
    }
    // 범용 생성 케이스, 200OK가 아닌 다른 성공상태를 응답으로 보내고 싶을 때 사용

    public static Response<Void> fail(BaseCode code) {
        return new Response<>(code.getStatus(), code.getCode(), code.getMessage(), null);
    }


    public static <T> Response<T> fail(BaseCode code, T data) {
        return new Response<>(code.getStatus(), code.getCode(), code.getMessage(), data);
    }
    // 일반적인 실패 케이스







}
