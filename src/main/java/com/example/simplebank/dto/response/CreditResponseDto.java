package com.example.simplebank.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreditResponseDto {
    private Long id;
    private Boolean working;
    private Integer yourNeed, salary, months;
}
