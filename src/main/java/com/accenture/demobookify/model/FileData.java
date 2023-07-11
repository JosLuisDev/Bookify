package com.accenture.demobookify.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "file_data")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secuencia")
    @SequenceGenerator(name = "secuencia", sequenceName = "ecuencia", initialValue = 6, allocationSize = 1)
    private Long id;

    private String name;
    private String type;
    private String filePath;

}
