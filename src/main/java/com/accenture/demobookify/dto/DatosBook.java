package com.accenture.demobookify.dto;

import com.accenture.demobookify.model.Book;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record DatosBook(
        @NotBlank(message = "The field title is required and not blank")
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
        double quantity_available,
        @NotBlank(message = "The field publicationYear is required and not blank")
        @Pattern(regexp = "[0-9]{4}", message = "The publicationYear need to use this format yyyy")
        String publicationYear,
        @NotBlank(message = "language must contain a value of the following 'English' or 'Spanish'")
        @Pattern(regexp = "^(English|Spanish)$", message = "language must contain a value of the following 'English' or 'Spanish'")
        String language) {

}
