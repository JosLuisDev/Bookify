package com.accenture.demobookify.service;

import com.accenture.demobookify.dto.DatosAuthor;
import com.accenture.demobookify.model.Author;
import com.accenture.demobookify.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService{

    private AuthorRepository authorRepository;
    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository){
        this.authorRepository=authorRepository;
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author getById(Long id) {
        return authorRepository.getReferenceById(id);
    }

    @Override
    public Long save(DatosAuthor datosAuthor) {
        Author author = new Author(datosAuthor);
        Author authorRes = authorRepository.save(author);
        return authorRes.getId();
    }

    @Override
    public Long update(Long id, DatosAuthor datosAuthor) {
        Author authorBD = getById(id);
        authorBD.setFirstname(datosAuthor.firstname());
        authorBD.setLastname(datosAuthor.lastname());
        authorBD.setBiography(datosAuthor.biography());
        return authorBD.getId();
    }

    @Override
    public void delete(Long id) {
        Author authorDB = getById(id);
        authorDB.setActive(false);
    }

    @Override
    public void physicalDelete(Long id) {
        authorRepository.deleteById(id);
    }
}
