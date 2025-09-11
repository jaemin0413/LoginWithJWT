package study.LoginWithJWT.global.apiPayload.exception;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import study.LoginWithJWT.global.apiPayload.code.ErrorCode;
import study.LoginWithJWT.global.apiPayload.response.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
// 컨트롤러에 흩어져 있는 예외처리 로직을 한 곳에 모아주기 위함
// 각 컨트롤러에서 공통적으로 발생하는 종류의 예외들을 한 곳에서 처리함으로써 컨트롤러는 비즈니스 로직에 집중하게 함

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

// ===================== 사용자 정의 예외 ======================

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Response<Void>> handleBusinessException(BusinessException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(Response.fail(ex.getCode()));
    }

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<Response<Void>> handleGlobalException(GlobalException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(Response.fail(ex.getCode()));
    }

    // ===================== Validation / 변환 오류 ======================

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Response.fail(ErrorCode.BAD_REQUEST, errors));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Response.fail(ErrorCode.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler({
            ConstraintViolationException.class,
            DataIntegrityViolationException.class,
            IllegalArgumentException.class,
            IncorrectResultSizeDataAccessException.class,
            InvalidDataAccessApiUsageException.class,
            NoSuchElementException.class
    })
    public ResponseEntity<Object> handleBadRequestExceptions(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Response.fail(ErrorCode.BAD_REQUEST, ex.getMessage()));
    }

    // ===================== 시스템 오류 (500) ======================

    @ExceptionHandler({
            BeanCreationException.class,
            ClassCastException.class,
            HttpMessageConversionException.class,
            JpaSystemException.class,
            NullPointerException.class,
            UnsatisfiedDependencyException.class
    })
    public ResponseEntity<Object> handleServerExceptions(Exception ex) {
        log.error("Internal server error: ", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.fail(ErrorCode.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }

    // ===================== 예기치 못한 예외 ======================

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Void>> handleUnknownException(Exception ex) {
        log.error("Unhandled exception occurred", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.fail(ErrorCode.INTERNAL_SERVER_ERROR));
    }

}
