package com.accenture.demobookify.service;

import com.accenture.demobookify.dto.DatosBook;
import com.accenture.demobookify.dto.DatosBookResponse;
import com.accenture.demobookify.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<DatosBookResponse> getAll();
    DatosBookResponse getById(Long id);
    List<Book> getByAuthorId(Long id);
    DatosBookResponse save(DatosBook datosBook);
    DatosBookResponse update(Long id, DatosBook datosBook);
    DatosBookResponse delete(Long id);
    void physicalDelete(Long id);
    void deleteByIdAuthorId(Long id);
}
