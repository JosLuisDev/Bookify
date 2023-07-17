package com.accenture.demobookify.dto;

import com.accenture.demobookify.util.OrderStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record DatosOrder(
        @NotNull(message = "The field user is required")
        Long user_id,
        @NotNull(message = "The field books_id is required")
        @NotEmpty(message = "The value of books_id must not be empty")
        List<Long> books_id,
        @NotEmpty(message = "The field shippingAddress must be provided")
        String shippingAddress,
        @Future(message = "The shipping date must be in the future.")
        LocalDate shippingDate,
        @NotNull(message = "The fiel status is required")
//        @Pattern(regexp = "^(PLACED|SHIPPED|DELIVERED|CANCELED)$")
        OrderStatus status) {
}
