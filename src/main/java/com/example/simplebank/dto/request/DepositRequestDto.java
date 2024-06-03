package com.example.simplebank.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DepositRequestDto {
    private Integer amount, months;
}
