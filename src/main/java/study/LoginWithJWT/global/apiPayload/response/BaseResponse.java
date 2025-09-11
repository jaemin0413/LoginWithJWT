package study.LoginWithJWT.global.apiPayload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseResponse <T> {

    private boolean success;
    private String message;
    private T data; //제네릭 사용

    public static <T> BaseResponse<T> onSuccess(T data) {
        return new BaseResponse<T>(true, "success", data);
    }

}
