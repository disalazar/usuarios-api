package com.smartjob.usuarios_api.interfaces.rest;

import com.smartjob.usuarios_api.application.CreateUserService;
import com.smartjob.usuarios_api.domain.model.User;
import com.smartjob.usuarios_api.interfaces.rest.dto.UserRequestDto;
import com.smartjob.usuarios_api.interfaces.rest.dto.UserResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private CreateUserService createUserService;

    @InjectMocks
    private UserController userController;

    @Test
    void testRegisterUser_ReturnsCreatedUser() {
        // ARRANGE
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setName("John Doe");
        requestDto.setEmail("john@example.com");
        requestDto.setPassword("Abc123!@#");

        User mockUser = new User();
        mockUser.setName("John Doe");
        mockUser.setEmail("john@example.com");

        when(createUserService.registerUser(any(UserRequestDto.class))).thenReturn(mockUser);

        // ACT
        ResponseEntity<UserResponseDto> response = userController.registerUser(requestDto);

        // ASSERT
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("John Doe", response.getBody().getName());
        assertEquals("john@example.com", response.getBody().getEmail());
    }
}
