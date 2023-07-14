package com.accenture.demobookify.dto;

import com.accenture.demobookify.model.Book;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record DatosBook(
        @NotBlank(message = "El campo title es obligatorio")
        @Pattern(regexp = "^.{0,100}$", message = "The title of the book must not exceed 100 characters")
        String title,
        @NotNull
        Long authorId,
        @NotBlank
        String description,
        @NotNull
        @Positive(message = "The price must be positive and different to 0")
        BigDecimal price,
        @NotNull
        @DecimalMin(value = "0", message = "The queantity_available must be a positive integer value")
        double quantity_available) {

}
