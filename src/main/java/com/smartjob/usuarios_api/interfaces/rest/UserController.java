package com.smartjob.usuarios_api.interfaces.rest;

import com.smartjob.usuarios_api.application.CreateUserService;
import com.smartjob.usuarios_api.domain.model.User;
import com.smartjob.usuarios_api.interfaces.rest.dto.UserRequestDto;
import com.smartjob.usuarios_api.interfaces.rest.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Endpoints para gestión de usuarios")
public class UserController {

    private final CreateUserService createUserService;

    @PostMapping
    @Operation(summary = "Registra un nuevo usuario")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRequestDto userDTO) {
        User user = createUserService.registerUser(userDTO);
        UserResponseDto response = UserResponseDto.fromEntity(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
