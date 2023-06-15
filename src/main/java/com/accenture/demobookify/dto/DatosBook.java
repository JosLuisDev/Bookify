package com.accenture.demobookify.dto;

import com.accenture.demobookify.model.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DatosBook(
        @NotBlank
        String title,
        @NotNull
        Long authorId,
        @NotBlank
        String description,
        @NotNull
        BigDecimal price,
        @NotNull
        Integer quantity_available) {

}
