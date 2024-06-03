package com.example.simplebank.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerRequestDto {
    private String firstName, lastName, fatherName, gender;
    private Integer age;
}
