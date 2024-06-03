package com.example.simplebank.controller;

import com.example.simplebank.dto.request.CustomerRequestDto;
import com.example.simplebank.dto.response.CustomerResponseDto;
import com.example.simplebank.entity.CustomerEntity;
import com.example.simplebank.exception.MyException;
import com.example.simplebank.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService service;

    @PostMapping("/create")
    public String create(@RequestBody CustomerRequestDto dto) {
        return service.create(dto);
    }

    @GetMapping("/read-all")
    public List<CustomerResponseDto> readAll() {
        return service.readAll();
    }

    @GetMapping("/read-by-id/{id}")
    public CustomerResponseDto readById(@PathVariable Long id) throws MyException {
        return service.readById(id);
    }

    @PutMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @RequestBody CustomerRequestDto dto) throws MyException {
        return service.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws MyException {
        return service.delete(id);
    }
}
