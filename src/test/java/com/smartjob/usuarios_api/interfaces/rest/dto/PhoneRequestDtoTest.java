package com.smartjob.usuarios_api.interfaces.rest.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PhoneRequestDtoTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void testPhoneRequestDtoGettersSetters() {
        String expectedNumber = "1234567";
        String expectedCityCode = "2";
        String expectedCountryCode = "56";

        PhoneRequestDto phoneDto = PhoneRequestDto.builder()
                .number(expectedNumber)
                .cityCode(expectedCityCode)
                .countryCode(expectedCountryCode)
                .build();

        assertEquals(expectedNumber, phoneDto.getNumber());
        assertEquals(expectedCityCode, phoneDto.getCityCode());
        assertEquals(expectedCountryCode, phoneDto.getCountryCode());
    }

    @Test
    void whenPhoneRequestDtoIsInvalid_thenValidationErrors() {
        PhoneRequestDto phoneDto = PhoneRequestDto.builder()
                .number("")
                .cityCode("")
                .countryCode("")
                .build();

        Set<ConstraintViolation<PhoneRequestDto>> violations = validator.validate(phoneDto);

        assertNotNull(violations);
        assertEquals(3, violations.size());
    }
}
