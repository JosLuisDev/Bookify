package com.accenture.demobookify.model;

import com.accenture.demobookify.dto.DatosBook;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "book")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secuencia")
    @SequenceGenerator(name = "secuencia", sequenceName = "ecuencia", initialValue = 6, allocationSize = 1)
    private Long id;
    private String title;
    @ManyToOne(cascade = CascadeType.ALL)//NO AGARRA EK CASCADE ALL SIGUE SURGIENDO ERROR DE CONSTRAINT
    @JoinColumn(name = "author_id")//La tabla book tendra la primary key de la tabla author
    private Author author;
    private String description;
    private BigDecimal price;
    private int quantity_available;
    @Column(name = "publication_year")
    private String publicationYear;
    private String language;
    private boolean isActive;

    public Book(DatosBook datosBook, Author author){
        this.title = datosBook.title();
        this.author = author;
        this.description = datosBook.description();
        this.price = datosBook.price();
        this.quantity_available = (int) datosBook.quantity_available();
        this.publicationYear = datosBook.publicationYear();
        this.language = datosBook.language();
        this.isActive = true;
    }

}
