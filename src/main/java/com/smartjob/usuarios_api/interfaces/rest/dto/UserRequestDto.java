package com.smartjob.usuarios_api.interfaces.rest.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDto {
    @NotBlank(message = "El dato 'name' es requerido")
    private String name;
    @NotBlank(message = "El dato 'email' es requerido")
    @Email(message = "El correo (dato 'email') debe tener un formato valido")
    private String email;
    @NotBlank(message = "El dato 'password' es requerido")
    private String password;
    @Valid
    @NotEmpty(message = "la lista de telefonos (dato phones) no puede ser vacia")
    private List<PhoneRequestDto> phones;
}
