package com.accenture.demobookify.model;

import com.accenture.demobookify.dto.DatosAuthor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "author")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    /*
    Cree una secuencia en BD para que el id autogenerado comience en 100 para evitar que los que agregamos en
    el archivo .sql puedan causar error a causa del id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secuencia")
    @SequenceGenerator(name = "secuencia", sequenceName = "ecuencia", initialValue = 6, allocationSize = 1)
    private Long id;
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank
    private String biography;
    private boolean isActive;

    public Author(DatosAuthor datosAuthor) {
        this.firstname = datosAuthor.firstname();
        this.lastname = datosAuthor.lastname();
        this.biography = datosAuthor.biography();
        this.isActive = true;
    }
}
