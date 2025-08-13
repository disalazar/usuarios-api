package com.smartjob.usuarios_api.domain.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PasswordValidator {

    private final Pattern passwordPattern;

    public PasswordValidator(@Value("${app.validation.password.regex}") String regex) {
        this.passwordPattern = Pattern.compile(regex);
    }

    public boolean isValid(String password) {
        return password != null && passwordPattern.matcher(password).matches();
    }
}
