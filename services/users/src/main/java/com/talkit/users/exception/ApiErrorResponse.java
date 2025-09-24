package com.talkit.users.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created on 22/09/2025 at 12:48
 * Author: theneuro
 */

@Data
@AllArgsConstructor
public class ApiErrorResponse {
    String requestId;
    String errorCode;
    String message;
    Integer statusCode;
    String statusName;
    String method;
    String path;
    LocalDateTime localDateTime;
}
