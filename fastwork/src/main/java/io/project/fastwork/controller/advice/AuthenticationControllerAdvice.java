package io.project.fastwork.controller.advice;

import io.project.fastwork.controller.exception.TokenRefreshException;
import io.project.fastwork.dto.response.ErrorMessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class AuthenticationControllerAdvice {
    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessageResponse handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
        return ErrorMessageResponse.builder()
                .errro_statusCode(HttpStatus.FORBIDDEN.value())
                .timestamp(new Date())
                .error_message(ex.getMessage())
                .error_description(request.getDescription(false)).build();
    }
}