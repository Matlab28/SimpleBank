package com.example.simplebank.service;

import com.example.simplebank.dto.request.DepositRequestDto;
import com.example.simplebank.dto.response.DepositResponseDto;
import com.example.simplebank.entity.DepositEntity;
import com.example.simplebank.exception.MyException;
import com.example.simplebank.repository.DepositRepository;
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
public class DepositService {
    private final ModelMapper modelMapper;
    private final DepositRepository depositRepository;


    public DepositService(ModelMapper modelMapper,
                          DepositRepository depositRepository) {
        this.modelMapper = modelMapper;
        this.depositRepository = depositRepository;
    }

    public String setDeposit(DepositRequestDto dto) {
        DepositEntity map = modelMapper.map(dto, DepositEntity.class);

        depositRepository.save(map);
        log.info("User set " + dto.getAmount() + " azn for " + dto.getMonths() + " months as a deposit.");
        return switch (dto.getMonths()) {
            case 3 -> "You set : " + dto.getAmount() +
                    " AZN.\nAfter 3 months you will get : " + ((dto.getAmount() * 103) / 100) + ".";
            case 6 -> "You set : " + dto.getAmount() +
                    " AZN.\nAfter 6 months you will get : " + ((dto.getAmount() * 106) / 100) + ".";
            case 9 -> "You set : " + dto.getAmount() +
                    " AZN.\nAfter 9 months you will get : " + ((dto.getAmount() * 109) / 100) + ".";
            case 12 -> "You set : " + dto.getAmount() +
                    " AZN.\nAfter 12 months you will get : " + ((dto.getAmount() * 112) / 100) + ".";
            default -> "You can set only 3, 6, 9, or 12 months...";
        };
    }

    public List<DepositResponseDto> readAll() {
        log.info("User's deposit info responded.");
        return depositRepository
                .findAll()
                .stream()
                .map(m -> modelMapper.map(m, DepositResponseDto.class))
                .collect(Collectors.toList());
    }

    public DepositResponseDto readById(Long id) throws MyException {
        DepositEntity deposit = depositRepository.findById(id)
                .orElseThrow(() -> new MyException("Deposit info not found by ID - " + id));

        log.info("'" + id + "' ID deposit info responded.");
        return modelMapper.map(deposit, DepositResponseDto.class);
    }
}
