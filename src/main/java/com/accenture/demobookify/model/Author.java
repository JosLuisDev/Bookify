package com.accenture.demobookify.model;

import com.accenture.demobookify.dto.DatosAuthor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "author")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    /*
    Cree una secuencia en BD para que el id autogenerado comience en 6 para evitar que los que agregamos en
    el archivo .sql puedan causar error a causa del id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secuencia")
    @SequenceGenerator(name = "secuencia", sequenceName = "ecuencia", initialValue = 6, allocationSize = 1)
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    private String nationality;
    private String biography;
    private String url;
    @OneToOne
    @JoinColumn(name = "image_id")
    private FileData fileData;
    private boolean isActive;

    public Author(DatosAuthor datosAuthor) {
        this.firstname = datosAuthor.firstname();
        this.lastname = datosAuthor.lastname();
        this.biography = datosAuthor.biography();
        this.isActive = true;
    }
}
