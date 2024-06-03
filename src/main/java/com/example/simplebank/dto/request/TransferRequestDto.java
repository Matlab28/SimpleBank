package com.example.simplebank.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransferRequestDto {
    private String toWho;
    private Double balance;
}
