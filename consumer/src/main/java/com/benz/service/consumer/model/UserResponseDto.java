package com.benz.service.consumer.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {

    private Integer status;
    private String message;
    private Object body;

}
