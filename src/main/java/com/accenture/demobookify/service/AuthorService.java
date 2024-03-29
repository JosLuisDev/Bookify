package com.accenture.demobookify.service;

import com.accenture.demobookify.dto.DatosAuthor;
import com.accenture.demobookify.exception.AuthorDataAlreadyExistException;
import com.accenture.demobookify.exception.DataNotFoundException;
import com.accenture.demobookify.exception.UrlNotAccesibleException;
import com.accenture.demobookify.model.Author;
import com.accenture.demobookify.model.FileData;

import java.util.List;

public interface AuthorService {
    List<Author> getAll();
    Author getById(Long id) throws DataNotFoundException;
    Long save(DatosAuthor datosAuthor) throws AuthorDataAlreadyExistException, UrlNotAccesibleException;
    Long update(Long id, DatosAuthor datosAuthor) throws DataNotFoundException;
    void delete(Long id) throws DataNotFoundException;
    Author updateImageAuthor(FileData fileData, Long idAuthor);
    void physicalDelete(Long id);
}
