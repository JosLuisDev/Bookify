package com.accenture.demobookify.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record DatosAuthor(
        @NotBlank
        @Pattern(regexp = "[a-zA-Z]+", message = "The first name attribute must contain only alphabet values.")
        String firstname,
        @NotBlank
        @Pattern(regexp = "[a-zA-Z]+", message = "The last name attribute must contain only alphabet values.")
        String lastname,
        @NotBlank(message = "The nationality must be specified")
        String nationality,
        @NotBlank(message = "biography must not be blank ")
        @Pattern(regexp = "^.{0,500}$", message = "The biography must not exceed 500 characters.")
        String biography) {

}
