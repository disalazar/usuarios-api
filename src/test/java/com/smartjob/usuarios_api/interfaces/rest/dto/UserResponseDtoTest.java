package com.smartjob.usuarios_api.interfaces.rest.dto;

import com.smartjob.usuarios_api.domain.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserResponseDtoTest {

    @Test
    void testFromEntity_AllFieldsMapped() {
        LocalDateTime now = LocalDateTime.now();

        User user = User.builder()
                .name("John Doe")
                .email("john@example.com")
                .password("Abc123@#")
                .created(now)
                .modified(now.plusMinutes(5))
                .lastLogin(now.plusMinutes(10))
                .token("some-jwt-token")
                .isActive(true)
                .build();

        UserResponseDto dto = UserResponseDto.fromEntity(user);

        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getName(), dto.getName());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getCreated(), dto.getCreated());
        assertEquals(user.getModified(), dto.getModified());
        assertEquals(user.getLastLogin(), dto.getLastLogin());
        assertEquals(user.getToken(), dto.getToken());
        assertTrue(dto.isActive());
    }
}
