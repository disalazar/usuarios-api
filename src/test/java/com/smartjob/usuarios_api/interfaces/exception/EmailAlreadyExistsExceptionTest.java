package com.smartjob.usuarios_api.interfaces.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailAlreadyExistsExceptionTest {

    @Test
    void testExceptionMessage() {
        String expectedMessage = "Email already registered";
        EmailAlreadyExistsException exception = new EmailAlreadyExistsException(expectedMessage);

        assertEquals(expectedMessage, exception.getMessage());
    }
}
