package com.myApp.task_management.Advice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    int status;
    String error;
    String message;

    public ErrorResponse(int status, String error, String message) {
        this.error = error;
        this.message = message;
        this.status = status;
    }
}
