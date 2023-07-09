package com.accenture.demobookify.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record DatosAuthor(
        @NotBlank
        @Pattern(regexp = "[a-zA-Z]+", message = "The first name attribute must contain only alphabet values.")
        String firstname,
        @NotBlank
        @Pattern(regexp = "[a-zA-Z]+", message = "The last name attribute must contain only alphabet values.")
        String lastname,
        @NotNull(message = "The date of birth must not be null")
        @Past(message = "The date of birth must be in the past and must be a valid date")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate dateOfBirth,
        @NotBlank(message = "The nationality must be specified")
        String nationality,
        @NotBlank(message = "biography must not be blank ")
        @Pattern(regexp = "^.{0,500}$", message = "The biography must not exceed 500 characters.")
        String biography) {

}
