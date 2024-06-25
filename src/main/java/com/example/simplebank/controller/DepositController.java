package com.example.simplebank.controller;

import com.example.simplebank.dto.request.DepositRequestDto;
import com.example.simplebank.dto.response.DepositResponseDto;
import com.example.simplebank.exception.MyException;
import com.example.simplebank.service.DepositService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deposit")
@SecurityRequirement(name = "Bearer Authentication")
public class DepositController {
    private final DepositService service;

    @PostMapping("/set-deposit")
    public String setDeposit(@RequestBody DepositRequestDto dto) {
        return service.setDeposit(dto);
    }

    @GetMapping("/read-all")
    public List<DepositResponseDto> readAll() {
        return service.readAll();
    }

    @GetMapping("/read-by-id/{id}")
    public DepositResponseDto readById(@PathVariable Long id) throws MyException {
        return service.readById(id);
    }
}
