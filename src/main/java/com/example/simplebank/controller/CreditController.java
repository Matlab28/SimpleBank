package com.example.simplebank.controller;

import com.example.simplebank.dto.request.CreditRequestDto;
import com.example.simplebank.dto.response.CreditResponseDto;
import com.example.simplebank.exception.MyException;
import com.example.simplebank.service.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/credit")
public class CreditController {
    private final CreditService service;

    @PostMapping("/get-credit")
    public String getCredit(@RequestBody CreditRequestDto dto) {
        return service.getCredit(dto);
    }

    @GetMapping("/read-all")
    public List<CreditResponseDto> readAll() {
        return service.readAll();
    }

    @GetMapping("/read-by-id/{id}")
    public CreditResponseDto readById(@PathVariable Long id) throws MyException {
        return service.readById(id);
    }
}
