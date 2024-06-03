package com.example.simplebank.service;

import com.example.simplebank.dto.request.CustomerRequestDto;
import com.example.simplebank.dto.response.CustomerResponseDto;
import com.example.simplebank.entity.CustomerEntity;
import com.example.simplebank.exception.MyException;
import com.example.simplebank.repository.CardRepository;
import com.example.simplebank.repository.CustomerRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Setter
@Getter
@Slf4j
public class CustomerService {
    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;
    private final CardRepository cardRepository;

    public CustomerService(ModelMapper modelMapper,
                           CustomerRepository customerRepository,
                           CardRepository cardRepository) {
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
        this.cardRepository = cardRepository;
    }

    public String create(CustomerRequestDto dto) {
        CustomerEntity map = modelMapper.map(dto, CustomerEntity.class);

        if (dto.getAge() < 18) {
            return "You must be at least 18 year-old for getting access...";
        } else if (!dto.getGender().equals("male") && !dto.getGender().equals("female")) {
            return "Please enter only male or female";
        }

        customerRepository.save(map);
        log.info("Customer info saved in DB.");
        return "Thank you " + dto.getFirstName() + "!\nYour information added successfully.";
    }

    public List<CustomerResponseDto> readAll() {
        log.info("All customer info responded.");
        return customerRepository
                .findAll()
                .stream()
                .map(m -> modelMapper.map(m, CustomerResponseDto.class))
                .collect(Collectors.toList());
    }

    public CustomerResponseDto readById(Long id) throws MyException {
        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(() -> new MyException("Customer not found by ID - " + id));

        log.info("'" + id + "' ID customer info responded.");
        return modelMapper.map(customer, CustomerResponseDto.class);
    }

    public String update(Long id, CustomerRequestDto dto) throws MyException {
        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(() -> new MyException("Customer not found by ID - " + id));

        modelMapper.map(dto, customer);
        customerRepository.save(customer);
        log.info("'" + id + "' ID user updated.");
        return "You were updated successfully!";
    }

    public String delete(Long id) throws MyException {
        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(() -> new MyException("Customer not found by ID - " + id));

        customerRepository.delete(customer);
        log.info("'" + id + "' ID customer deleted.");
        return "You were deleted successfully!";
    }
}
