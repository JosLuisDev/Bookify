package com.accenture.demobookify.dto;

import com.accenture.demobookify.model.FileData;
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
        @NotBlank
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Invalid e-mail")
        String email,
        @NotNull(message = "The date of birth must not be null")
        @Past(message = "The date of birth must be in the past.")
//        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate dateOfBirth,
        @NotBlank(message = "The nationality must be specified")
        String nationality,
        @NotBlank(message = "biography must not be blank ")
        @Pattern(regexp = "^.{0,500}$", message = "The biography must not exceed 500 characters.")
        String biography,
        /*
        La expresión regular:
        "^(http[s]?://)?([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?$"
        Esta otra expresion regular acepta las siguientes:
        http://example.com
        https://example.es
        https://www.example.com
        http://example.com/path/to/resource
        https://example.com/?param=value
        http://example.com/path/to/resource?param=value
         */
        @Pattern(regexp = "^(http[s]?://)([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?$", message = "URL format invalid")
        String url,
        FileData fileData) {

}
