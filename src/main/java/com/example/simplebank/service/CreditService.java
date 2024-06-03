package com.example.simplebank.service;

import com.example.simplebank.dto.request.CreditRequestDto;
import com.example.simplebank.dto.response.CreditResponseDto;
import com.example.simplebank.entity.CreditEntity;
import com.example.simplebank.exception.MyException;
import com.example.simplebank.repository.CreditRepository;
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
public class CreditService {
    private final ModelMapper modelMapper;
    private final CreditRepository creditRepository;

    public CreditService(ModelMapper modelMapper,
                         CreditRepository creditRepository) {
        this.modelMapper = modelMapper;
        this.creditRepository = creditRepository;
    }

    public String getCredit(CreditRequestDto dto) {
        CreditEntity map = modelMapper.map(dto, CreditEntity.class);

        if (dto.getWorking().equals(false)) {
            return "Unfortunately you have to work for getting credit...";
        } else if (dto.getYourNeed() < dto.getSalary() * 6) {
            return "You can get loan utmost 6 times of your salary";
        }
        creditRepository.save(map);
        log.info("User got '" + dto.getYourNeed() + "' AZN for " + dto.getMonths() + " months.");
        return switch (dto.getMonths()) {
            case 3 -> "Your loan will be - " + ((dto.getYourNeed() * 107) / 100) +
                    "\nFor 3 months, '" + (((dto.getYourNeed() * 107) / 100) / 3) + "' in a month";
            case 6 -> "Your loan will be - " + ((dto.getYourNeed() * 109) / 100) +
                    "\nFor 6 months, '" + (((dto.getYourNeed() * 109) / 100) / 6) + "' in a month";
            case 12 -> "Your loan will be - " + ((dto.getYourNeed() * 112) / 100) +
                    "\nFor 12 months, '" + (((dto.getYourNeed() * 112) / 100) / 12) + "' in a month";
            case 18 -> "Your loan will be - " + ((dto.getYourNeed() * 114) / 100) +
                    "\nFor 18 months, '" + (((dto.getYourNeed() * 114) / 100) / 18) + "' in a month";
            case 24 -> "Your loan will be - " + ((dto.getYourNeed() * 116) / 100) +
                    "\nFor 24 months, '" + (((dto.getYourNeed() * 116) / 100) / 24) + "' in a month";
            case 30 -> "Your loan will be - " + ((dto.getYourNeed() * 118) / 100) +
                    "\nFor 30 months, '" + (((dto.getYourNeed() * 118) / 100) / 30) + "' in a month";
            case 36 -> "Your loan will be - " + ((dto.getYourNeed() * 120) / 100) +
                    "\nFor 36 months, '" + (((dto.getYourNeed() * 120) / 100) / 36) + "' in a month";
            default -> "Please enter only 3, 6, 12, 18, 24, 30, or 36.";
        };
    }

    public List<CreditResponseDto> readAll() {
        log.info("Credit info responded.");
        return creditRepository
                .findAll()
                .stream()
                .map(m -> modelMapper.map(m, CreditResponseDto.class))
                .collect(Collectors.toList());
    }

    public CreditResponseDto readById(Long id) throws MyException {
        CreditEntity credit = creditRepository.findById(id)
                .orElseThrow(() -> new MyException("Credit info not found by ID - " + id));

        log.info("'" + id + "' ID credit info responded.");
        return modelMapper.map(credit, CreditResponseDto.class);
    }
}
