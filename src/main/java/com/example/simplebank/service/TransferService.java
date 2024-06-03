package com.example.simplebank.service;

import com.example.simplebank.dto.request.TransferRequestDto;
import com.example.simplebank.dto.response.TransferResponseDto;
import com.example.simplebank.entity.TransferEntity;
import com.example.simplebank.exception.MyException;
import com.example.simplebank.repository.TransferRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Setter
@Getter
@Slf4j
public class TransferService {
    private final ModelMapper modelMapper;
    private final TransferRepository transferRepository;

    public TransferService(ModelMapper modelMapper,
                           TransferRepository transferRepository) {
        this.modelMapper = modelMapper;
        this.transferRepository = transferRepository;
    }

    public String sendMoney(TransferRequestDto dto) {
        StringBuilder builder = new StringBuilder();
        TransferEntity map = modelMapper.map(dto, TransferEntity.class);

        try {
            if (dto.getToWho().matches(".*[A-Za-z].*")) {
                return "Card number cannot contain any letters...";
            } else if (!dto.getToWho().matches(".*\\d+.*")) {
                return "Card number only contains digits...";
            } else if (dto.getToWho().length() != 16) {
                return "Card number's length must be 16 characters...";
            }
        } catch (Exception e) {
            log.error("Something went wrong with transferring money...");
            return "Invalid inputs...Something went wrong...";
        }

        for (int i = 0; i < dto.getToWho().length(); i += 4) {
            int endIndex = Math.min(i + 4, dto.getToWho().length());
            String substring = dto.getToWho().substring(i, endIndex);
            builder.append(substring);

            if (endIndex < dto.getToWho().length()) {
                builder.append(" ");
            }
        }

        transferRepository.save(map);
        log.info("'" + dto.getBalance() + "' AZN sent to '" + builder + "'.");
        return "'" + dto.getBalance() + "' AZN successfully sent to '" + builder + "' successfully!";

    }

    public List<TransferResponseDto> readAll() {
        log.info("All transfer info responded.");
        return transferRepository
                .findAll()
                .stream()
                .map(m -> modelMapper.map(m, TransferResponseDto.class))
                .toList();
    }

    public TransferResponseDto readById(Long id) throws MyException {
        TransferEntity transfer = transferRepository.findById(id)
                .orElseThrow(() -> new MyException("Transfer info not found by ID - " + id));

        log.info("'" + id + "' ID transfer info responded.");
        return modelMapper.map(transfer, TransferResponseDto.class);
    }
}
