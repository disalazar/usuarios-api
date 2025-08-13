package com.smartjob.usuarios_api.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.smartjob.usuarios_api.domain.model.Phone;
import com.smartjob.usuarios_api.domain.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void whenFindByEmail_thenReturnUser() {
        LocalDateTime now = LocalDateTime.now();

        Phone phone = Phone.builder()
                .number("1234567")
                .cityCode("1")
                .countryCode("57")
                .build();

        User user = User.builder()
                .name("Test User")
                .email("testuser@example.com")
                .password("Password1!")
                .phones(List.of(phone))
                .created(now)
                .modified(now)
                .lastLogin(now)
                .token(UUID.randomUUID().toString())
                .isActive(true)
                .build();

        user = userRepository.save(user);

        Optional<User> found = userRepository.findByEmail("testuser@example.com");

        assertThat(found).isPresent();
        User foundUser = found.get();

        assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(foundUser.getName()).isEqualTo(user.getName());
        assertThat(foundUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(foundUser.getPhones()).isNotEmpty();
        assertThat(foundUser.getPhones().get(0).getNumber()).isEqualTo("1234567");
        assertThat(foundUser.getCreated()).isEqualTo(user.getCreated());
        assertThat(foundUser.getModified()).isEqualTo(user.getModified());
        assertThat(foundUser.getLastLogin()).isEqualTo(user.getLastLogin());
        assertThat(foundUser.getToken()).isEqualTo(user.getToken());
        assertThat(foundUser.isActive()).isTrue();
    }

    @Test
    void whenFindByEmailNotExists_thenReturnEmpty() {
        Optional<User> found = userRepository.findByEmail("nonexistent@example.com");
        assertThat(found).isEmpty();
    }
}
