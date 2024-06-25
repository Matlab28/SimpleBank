package com.example.simplebank.controller;

import com.example.simplebank.dto.request.TransferRequestDto;
import com.example.simplebank.dto.response.TransferResponseDto;
import com.example.simplebank.exception.MyException;
import com.example.simplebank.service.TransferService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transfer")
@SecurityRequirement(name = "Bearer Authentication")
public class TransferController {
    private final TransferService service;

    @PostMapping("/send-money")
    public String sendMoney(@RequestBody TransferRequestDto dto) {
        return service.sendMoney(dto);
    }

    @GetMapping("/read-all")
    public List<TransferResponseDto> readAll() {
        return service.readAll();
    }

    @PutMapping("/read-by-id/{id}")
    public TransferResponseDto readById(@PathVariable Long id) throws MyException {
        return service.readById(id);
    }
}
