package com.smartjob.usuarios_api.application;

import com.smartjob.usuarios_api.domain.model.Phone;
import com.smartjob.usuarios_api.domain.model.User;
import com.smartjob.usuarios_api.domain.repository.UserRepository;
import com.smartjob.usuarios_api.domain.service.PasswordValidator;
import com.smartjob.usuarios_api.infrastructure.security.JwtTokenProvider;
import com.smartjob.usuarios_api.interfaces.exception.EmailAlreadyExistsException;
import com.smartjob.usuarios_api.interfaces.rest.dto.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateUserService {

    private final UserRepository userRepository;
    private final PasswordValidator passwordValidator;
    private final JwtTokenProvider jwtTokenProvider;

    public User registerUser(UserRequestDto userDTO) {
        if (!passwordValidator.isValid(userDTO.getPassword())) {
            throw new IllegalArgumentException("La clave (dato 'password') no cumple con el formato requerido");
        }
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("El correo (dato 'email') ya se encuentra registrado");
        }

        List<Phone> phones = userDTO.getPhones().stream()
                .map(phoneDto -> Phone.builder()
                        .number(phoneDto.getNumber())
                        .cityCode(phoneDto.getCityCode())
                        .countryCode(phoneDto.getCountryCode())
                        .build())
                .toList();
        LocalDateTime nowLocalDate = LocalDateTime.now();
        String jwtToken = jwtTokenProvider.generateToken(userDTO.getEmail());

        User user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .phones(phones)
                .created(nowLocalDate)
                .modified(nowLocalDate)
                .lastLogin(nowLocalDate)
                .token(jwtToken)
                .isActive(true)
                .build();

        return userRepository.save(user);

    }
}
