package com.smartjob.usuarios_api.domain.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PasswordValidatorTest {

    @Value("${app.validation.password.regex}")
    private String passwordPattern;

    @Autowired
    private PasswordValidator passwordValidator;

    @Test
    void shouldAcceptValidPassword() {
        assertTrue(passwordValidator.isValid("Hunter%28"));
        assertTrue(passwordValidator.isValid("Passw0rd!"));
    }

    @Test
    void shouldRejectInvalidPassword() {
        assertFalse(passwordValidator.isValid("short1!"));
        assertFalse(passwordValidator.isValid("NoNumber!"));
        assertFalse(passwordValidator.isValid("nonumber123"));
    }
}
