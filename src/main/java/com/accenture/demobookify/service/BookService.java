package com.accenture.demobookify.service;

import com.accenture.demobookify.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAll();
    Optional<Book> getById(Long id);
    List<Book> getByAuthorId(Long id);
    Long save(Book book);
    Long update(Long id, Book book);
    void delete(Long id);
    void physicalDelete(Long id);
    void deleteByIdAuthorId(Long id);
}
