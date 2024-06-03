package com.example.simplebank.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreditRequestDto {
    private Boolean working;
    private Integer yourNeed, salary, months;
}
