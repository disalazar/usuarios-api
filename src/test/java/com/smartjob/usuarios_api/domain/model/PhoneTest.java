package com.smartjob.usuarios_api.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    void testPhoneBuilderAndGetters() {
        UUID id = UUID.randomUUID();

        Phone phone = Phone.builder()
                .id(id)
                .number("1234567")
                .cityCode("1")
                .countryCode("57")
                .build();

        assertThat(phone.getId()).isEqualTo(id);
        assertThat(phone.getNumber()).isEqualTo("1234567");
        assertThat(phone.getCityCode()).isEqualTo("1");
        assertThat(phone.getCountryCode()).isEqualTo("57");
    }
}
