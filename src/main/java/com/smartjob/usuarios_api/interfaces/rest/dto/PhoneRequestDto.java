package com.smartjob.usuarios_api.interfaces.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneRequestDto {
    @NotBlank(message = "El dato 'number' del telefono es requerido")
    private String number;
    @NotBlank(message = "El dato 'cityCode' del telefono es requerido")
    private String cityCode;
    @NotBlank(message = "El dato 'countryCode' del telefono es requerido")
    private String countryCode;
}