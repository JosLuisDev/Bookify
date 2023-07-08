package com.accenture.demobookify.repository;

import com.accenture.demobookify.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByFirstnameAndLastnameIgnoreCase(String firstName, String lastName);
}
