package com.accenture.demobookify.service;

import com.accenture.demobookify.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> getAll();
    Optional<Author> getById(Long id);
    Long save(Author author);
    Long update(Long id, Author author);
    void delete(Long id);
    void physicalDelete(Long id);
}
