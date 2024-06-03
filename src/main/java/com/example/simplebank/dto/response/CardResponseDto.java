package com.example.simplebank.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CardResponseDto {
    private Long id;
    private String cardNum, cvc, expDate;
}
