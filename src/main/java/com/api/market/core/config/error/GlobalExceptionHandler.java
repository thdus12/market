package com.api.market.core.config.error;

import com.api.market.core.config.error.entity.ApiException;
import com.api.market.core.config.error.entity.ErrorCode;
import com.api.market.core.config.error.entity.ErrorEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * @Valid 또는 @Validated 바인딩 에러 처리
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        BindingResult bindingResult = e.getBindingResult();
        FieldError fieldError = bindingResult.getFieldErrors().get(0);
        return ErrorEntity.status(ErrorCode.BAD_VALID).body(fieldError.getField() + ":" + fieldError.getDefaultMessage());
    }

    /**
     * @ModelAttribute 바인딩 에러 처리
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity handleBindException(BindException e) {
        log.error("handleBindException", e);
        BindingResult bindingResult = e.getBindingResult();
        FieldError fieldError = bindingResult.getFieldErrors().get(0);
        return ErrorEntity.status(ErrorCode.BAD_VALID).body(fieldError.getField() + ":" + fieldError.getDefaultMessage());
    }

    /**
     * 비즈니스 로직 예외 처리
     */
    @ExceptionHandler(ApiException.class)
    protected ResponseEntity handleApiException(ApiException e) {
        log.error("ApiException - {}", e.getMessage());
        return ErrorEntity.status(e.getErrorCode()).body(e.getBody());
    }

    /**
     * RuntimeException 처리
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleRuntimeException(RuntimeException e) {
        if (e instanceof ApiException) {
            return this.handleApiException((ApiException) e);
        }
        if (e.getCause() instanceof ApiException) {
            return this.handleApiException((ApiException) e.getCause());
        }
        log.error("handleRuntimeException", e);
        return ErrorEntity.status(ErrorCode.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 데이터베이스 제약조건 예외 처리
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        String message = e.getMostSpecificCause().getMessage();
        if (message.startsWith("Duplicate entry")) {
            return ErrorEntity.status(ErrorCode.DUPLICATE_KEY).body(null);
        }
        if (message.startsWith("Cannot delete or update a parent row")) {
            return ErrorEntity.status(ErrorCode.EXIST_PARENT).body(null);
        }
        log.error("DataIntegrityViolationException", e);
        return ErrorEntity.status(ErrorCode.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 나머지 모든 예외 처리
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity handleGlobalException(Exception e) {
        log.error("handleGlobalException", e);
        return ErrorEntity.status(ErrorCode.INTERNAL_SERVER_ERROR).body(null);
    }
}