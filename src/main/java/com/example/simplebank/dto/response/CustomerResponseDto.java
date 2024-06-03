package com.example.simplebank.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerResponseDto {
    private Long id;
    private String firstName, lastName, fatherName, gender;
    private Integer age;
}
