package com.smartjob.usuarios_api.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    void testUserBuilderAndGetters() {
        Phone phone = Phone.builder()
                .number("1234567")
                .cityCode("1")
                .countryCode("57")
                .build();

        LocalDateTime now = LocalDateTime.now();

        User user = User.builder()
                .id(UUID.randomUUID())
                .name("John Doe")
                .email("john.doe@example.com")
                .password("Secret123!")
                .phones(List.of(phone))
                .created(now)
                .modified(now)
                .lastLogin(now)
                .token("some-token")
                .isActive(true)
                .build();

        assertThat(user.getId()).isNotNull();
        assertThat(user.getName()).isEqualTo("John Doe");
        assertThat(user.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(user.getPassword()).isEqualTo("Secret123!");
        assertThat(user.getPhones()).hasSize(1);
        assertThat(user.getPhones().get(0).getNumber()).isEqualTo("1234567");
        assertThat(user.getCreated()).isEqualTo(now);
        assertThat(user.getModified()).isEqualTo(now);
        assertThat(user.getLastLogin()).isEqualTo(now);
        assertThat(user.getToken()).isEqualTo("some-token");
        assertThat(user.isActive()).isTrue();
    }

    @Test
    void testPreUpdateSetsModified() throws InterruptedException {
        User user = new User();
        user.setModified(LocalDateTime.now().minusDays(1));

        LocalDateTime beforeUpdate = user.getModified();

        Thread.sleep(10); // aseguro que pase algo de tiempo para el cambio de timestamp
        user.preUpdate();

        assertThat(user.getModified()).isAfter(beforeUpdate);
    }
}
