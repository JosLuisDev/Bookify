package com.accenture.demobookify.dto;

import com.accenture.demobookify.model.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DatosBookResponse (Long id, String title, Long authorId, String description, BigDecimal price, int quantity_available, boolean isActive){

    public DatosBookResponse(Book book){
        this(book.getId(), book.getTitle(), book.getAuthor().getId(), book.getDescription(),
                book.getPrice(), book.getQuantity_available(), book.isActive());
    }

}
