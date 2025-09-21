package com.project.springcloud.book.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuccessResponse {

    private String statusCode;
    private String statusMsg;
}
