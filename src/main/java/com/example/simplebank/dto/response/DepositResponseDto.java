package com.example.simplebank.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DepositResponseDto {
    private Long id;
    private Integer amount, months;
}
