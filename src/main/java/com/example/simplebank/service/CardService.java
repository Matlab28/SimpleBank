package com.example.simplebank.service;

import com.example.simplebank.dto.request.CardRequestDto;
import com.example.simplebank.dto.response.CardResponseDto;
import com.example.simplebank.entity.CardEntity;
import com.example.simplebank.exception.MyException;
import com.example.simplebank.repository.CardRepository;
import com.example.simplebank.repository.CustomerRepository;
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
public class CardService {
    private final ModelMapper modelMapper;
    private final CardRepository cardRepository;
    private final CustomerRepository customerRepository;

    public CardService(ModelMapper modelMapper,
                       CardRepository cardRepository,
                       CustomerRepository customerRepository) {
        this.modelMapper = modelMapper;
        this.cardRepository = cardRepository;
        this.customerRepository = customerRepository;
    }

    public String create(CardRequestDto dto) {
        StringBuilder builder = new StringBuilder();
        CardEntity map = modelMapper.map(dto, CardEntity.class);
//        long currentTime = System.currentTimeMillis();

        if (dto.getCardNum().matches(".*[A-Za-z].*")) {
            return "Card number cannot contain any letters...";
        } else if (!dto.getCardNum().matches(".*\\d+.*")) {
            return "Card number only contains digits...";
        } else if (dto.getCardNum().length() != 16) {
            return "Card number's length must be 16 characters...";
        } else if (dto.getCvc().matches(".*[A-Za-z].*")) {
            return "Card CVC cannot contain any letters...";
        } else if (!dto.getCvc().matches(".*\\d+.*")) {
            return "Card CVC only contains digits...";
        } else if (dto.getCvc().length() != 3) {
            return "CVC number must be 3 digits...";
        }
//        else
//        if (Long.parseLong(dto.getExpDate()) < currentTime) {
//            return "Please enter the right time...";
//        }

        for (int i = 0; i < dto.getCardNum().length(); i += 4) {
            int endIndex = Math.min(i + 4, dto.getCardNum().length());
            String substring = dto.getCardNum().substring(i, endIndex);
            builder.append(substring);

            if (endIndex < dto.getCardNum().length()) {
                builder.append(" ");
            }
        }

        cardRepository.save(map);
        log.info("Card info saved in DB.");
        return "Card info saved in database.\n\nYour card number : " + builder +
                "\nYour card CVC : " + dto.getCvc() + "\nYour card expiry date : " + dto.getExpDate();
    }

    public List<CardResponseDto> readAll() {
        log.info("All card info responded.");
        return cardRepository
                .findAll()
                .stream()
                .map(m -> modelMapper.map(m, CardResponseDto.class))
                .toList();
    }

    public CardResponseDto readById(Long id) throws MyException {
        CardEntity card = cardRepository.findById(id)
                .orElseThrow(() -> new MyException("Card not found by ID - " + id));

        log.info("'" + id + "' ID card info responded.");
        return modelMapper.map(card, CardResponseDto.class);
    }

    public String update(Long id, CardRequestDto dto) throws MyException {
        CardEntity card = cardRepository.findById(id)
                .orElseThrow(() -> new MyException("Card not found by ID - " + id));

        modelMapper.map(dto, card);
        cardRepository.save(card);
        log.info("'" + id + "' ID card updated.");
        return "Card information updated successfully!";
    }

    public String delete(Long id) throws MyException {
        CardEntity card = cardRepository.findById(id)
                .orElseThrow(() -> new MyException("Card not found by ID - " + id));

        cardRepository.delete(card);
        log.info("'" + id + "' ID card deleted.");
        return "Card information  deleted successfully1";
    }
}
