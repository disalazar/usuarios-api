package com.smartjob.usuarios_api.application;

import com.smartjob.usuarios_api.domain.model.User;
import com.smartjob.usuarios_api.domain.repository.UserRepository;
import com.smartjob.usuarios_api.domain.service.PasswordValidator;
import com.smartjob.usuarios_api.infrastructure.security.JwtTokenProvider;

import com.smartjob.usuarios_api.interfaces.rest.dto.PhoneRequestDto;
import com.smartjob.usuarios_api.interfaces.rest.dto.UserRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;


class CreateUserServiceTest {

    private UserRepository userRepository;
    private PasswordValidator passwordValidator;
    private CreateUserService createUserService;
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordValidator = mock(PasswordValidator.class);
        jwtTokenProvider = mock(JwtTokenProvider.class);

        createUserService = new CreateUserService(userRepository, passwordValidator, jwtTokenProvider);
    }

    @Test
    void registerUser_shouldSaveUser_whenValidData() {
        // Arrange
        UserRequestDto dto = new UserRequestDto();
        dto.setName("Diego");
        dto.setEmail("diego@test.com");
        dto.setPassword("Valid123!");
        dto.setPhones(Collections.singletonList(new PhoneRequestDto("123456", "2", "56")));

        when(passwordValidator.isValid(dto.getPassword())).thenReturn(true);
        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(jwtTokenProvider.generateToken(anyString())).thenReturn("mocked-jwt-token");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User savedUser = createUserService.registerUser(dto);

        // Assert
        assertNotNull(savedUser);
        assertEquals(dto.getName(), savedUser.getName());
        assertEquals(dto.getEmail(), savedUser.getEmail());
        assertEquals(dto.getPassword(), savedUser.getPassword());
        assertTrue(savedUser.isActive());
        assertEquals("mocked-jwt-token", savedUser.getToken());

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertEquals("Diego", captor.getValue().getName());
    }

    @Test
    void registerUser_shouldThrowException_whenEmailInvalid() {
        UserRequestDto dto = new UserRequestDto();
        dto.setEmail("bad-email");
        dto.setPassword("Valid123!");

        assertThrows(IllegalArgumentException.class, () -> createUserService.registerUser(dto));
    }
}
