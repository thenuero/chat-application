package com.talkit.users.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Created on 22/09/2025 at 12:42
 * Author: theneuro
 */
@Getter
@AllArgsConstructor
public class UserException extends RuntimeException {
    private String errorCode; // App specific
    private String message;
    private HttpStatus httpStatus;
}
