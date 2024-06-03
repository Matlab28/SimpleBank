package com.example.simplebank.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CardRequestDto {
    private String cardNum, cvc, expDate;
}
