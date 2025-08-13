package com.smartjob.usuarios_api.interfaces.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void whenEmailAlreadyExistsException_thenReturnsConflict() {
        String msg = "Email already registered";
        EmailAlreadyExistsException ex = new EmailAlreadyExistsException(msg);

        ResponseEntity<Map<String, String>> response = handler.handleEmailExists(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).containsEntry("mensaje", msg);
    }

    @Test
    void whenIllegalArgumentException_thenReturnsBadRequest() {
        String msg = "Invalid argument";
        IllegalArgumentException ex = new IllegalArgumentException(msg);

        ResponseEntity<Map<String, String>> response = handler.handleInvalidArgument(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).containsEntry("mensaje", msg);
    }

    @Test
    void whenOtherException_thenReturnsInternalServerError() {
        Exception ex = new Exception("Some error");

        ResponseEntity<Map<String, String>> response = handler.handleOthers(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).containsEntry("mensaje", "Error interno del servidor");
    }
}
