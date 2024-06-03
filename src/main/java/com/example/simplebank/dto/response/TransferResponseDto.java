package com.example.simplebank.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransferResponseDto {
    private Long id;
    private String toWho;
    private Double balance;
}
