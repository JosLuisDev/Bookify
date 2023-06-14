package com.accenture.demobookify.dto;

import jakarta.validation.constraints.NotBlank;

public record DatosAuthor(
        @NotBlank
        String firstname,
        @NotBlank
        String lastname,
        @NotBlank
        String biography) {

}
