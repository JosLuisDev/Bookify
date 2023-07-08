package com.accenture.demobookify.service;

import com.accenture.demobookify.dto.DatosAuthor;
import com.accenture.demobookify.exception.AuthorAlreadyExistException;
import com.accenture.demobookify.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> getAll();
    Author getById(Long id);
    Long save(DatosAuthor datosAuthor) throws AuthorAlreadyExistException;
    Long update(Long id, DatosAuthor datosAuthor);
    void delete(Long id);
    void physicalDelete(Long id);
}
