package com.example.simplebank.controller;

import com.example.simplebank.dto.request.CardRequestDto;
import com.example.simplebank.dto.response.CardResponseDto;
import com.example.simplebank.exception.MyException;
import com.example.simplebank.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/card")
public class CardController {
    private final CardService service;

    @PostMapping("/create")
    public String create(@RequestBody CardRequestDto dto) {
        return service.create(dto);
    }

    @GetMapping("/read-all")
    public List<CardResponseDto> readAll() {
        return service.readAll();
    }

    @GetMapping("/read-by-id/{id}")
    public CardResponseDto readById(@PathVariable Long id) throws MyException {
        return service.readById(id);
    }

    @PutMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @RequestBody CardRequestDto dto) throws MyException {
        return service.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws MyException {
        return service.delete(id);
    }
}
