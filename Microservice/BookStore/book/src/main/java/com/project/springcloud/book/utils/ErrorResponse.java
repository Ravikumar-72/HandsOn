package com.project.springcloud.book.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private HttpStatus code;
    private String path;
    private String errorMsg;
    private LocalDateTime time;
}
