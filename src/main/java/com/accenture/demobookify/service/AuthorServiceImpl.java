package com.accenture.demobookify.service;

import com.accenture.demobookify.model.Author;
import com.accenture.demobookify.repository.AuthorRepository;
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
    public Optional<Author> getById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public Long save(Author author) {
        Author authorRes = authorRepository.save(author);
        return authorRes.getId();
    }

    @Override
    public Long update(Long id, Author author) {
        Optional<Author> authorOpt = getById(id);
        if(authorOpt.isPresent()){
            Author authorBD = authorOpt.get();
            authorBD.setFirstname(author.getFirstname());
            authorBD.setLastname(author.getLastname());
            authorBD.setBiography(author.getBiography());
            authorRepository.save(authorBD);
            return authorBD.getId();
        }
        return 0L;
    }

    @Override
    public void delete(Long id) {
        Optional<Author> authorOpt = getById(id);
        if (authorOpt.isPresent()){
            Author authorBD = authorOpt.get();
            authorBD.setActive(false);
            authorRepository.save(authorBD);
        }
    }

    @Override
    public void physicalDelete(Long id) {
        authorRepository.deleteById(id);
    }
}
