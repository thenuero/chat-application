package com.talkit.users.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

/**
 * Created on 23/09/2025 at 12:05
 * Author: theneuro
 */
@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApplicationExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiErrorResponse> handleApplicationException(HttpServletRequest request, UserException userException) {

        log.warn("Exception occurred in user service [{} {}]: {}", request.getMethod(), request.getRequestURI(),
                userException.getMessage(), userException);

        String requestID = getRequestID(request);

        var response = new ApiErrorResponse(requestID,
                userException.getErrorCode(),
                userException.getMessage(),
                userException.getHttpStatus().value(),
                userException.getHttpStatus().name(),
                request.getMethod(),
                request.getRequestURI(),
                LocalDateTime.now(ZoneOffset.UTC));

        return ResponseEntity
                .status(userException.getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(HttpServletRequest request,
                                                    Exception exception) {

        log.error("Exception occurred in user service [{} {}]: {}", request.getMethod(), request.getRequestURI(),
                exception.getMessage(), exception);

        String requestID = getRequestID(request);

        var response = new ApiErrorResponse(requestID,
                "USER_SERVICE_DOWN",
                "User service down",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                request.getMethod(),
                request.getRequestURI(),
                LocalDateTime.now(ZoneOffset.UTC));

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    private static String getRequestID(HttpServletRequest request) {
        String requestID = request.getHeader("X-Request-ID");
        requestID = requestID == null ? UUID.randomUUID().toString() : requestID;
        return requestID;
    }
}
