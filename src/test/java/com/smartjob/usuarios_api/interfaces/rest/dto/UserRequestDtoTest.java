package com.smartjob.usuarios_api.interfaces.rest.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserRequestDtoTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void testUserRequestDtoGettersSetters() {
        String expectedName = "Jane Doe";
        String expectedEmail = "jane@example.com";
        String expectedPassword = "Abc123!@#";

        String phoneNumber1 = "1234567";
        String cityCode1 = "1";
        String countryCode1 = "57";

        String phoneNumber2 = "7654321";
        String cityCode2 = "2";
        String countryCode2 = "58";

        PhoneRequestDto phone1 = PhoneRequestDto.builder()
                .number(phoneNumber1)
                .cityCode(cityCode1)
                .countryCode(countryCode1)
                .build();

        PhoneRequestDto phone2 = PhoneRequestDto.builder()
                .number(phoneNumber2)
                .cityCode(cityCode2)
                .countryCode(countryCode2)
                .build();

        UserRequestDto dto = UserRequestDto.builder()
                .name(expectedName)
                .email(expectedEmail)
                .password(expectedPassword)
                .phones(List.of(phone1, phone2))
                .build();

        assertEquals(expectedName, dto.getName());
        assertEquals(expectedEmail, dto.getEmail());
        assertEquals(expectedPassword, dto.getPassword());
        assertNotNull(dto.getPhones());
        assertEquals(2, dto.getPhones().size());

        assertEquals(phoneNumber1, dto.getPhones().get(0).getNumber());
        assertEquals(countryCode1, dto.getPhones().get(0).getCountryCode());
        assertEquals(cityCode1, dto.getPhones().get(0).getCityCode());

        assertEquals(phoneNumber2, dto.getPhones().get(1).getNumber());
        assertEquals(countryCode2, dto.getPhones().get(1).getCountryCode());
        assertEquals(cityCode2, dto.getPhones().get(1).getCityCode());
    }

    @Test
    void whenUserRequestDtoIsInvalid_thenValidationErrors() {
        UserRequestDto dto = UserRequestDto.builder()
                .name("")
                .email("correo-invalido")
                .password("")
                .phones(List.of(
                        PhoneRequestDto.builder()
                                .number("")
                                .cityCode("")
                                .countryCode("")
                                .build()
                ))
                .build();

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        violations.forEach(v -> System.out.println(v.getPropertyPath() + ": " + v.getMessage()));
        assertEquals(6, violations.size());
    }
}
