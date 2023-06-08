package com.accenture.demobookify.service;

import com.accenture.demobookify.model.Book;
import com.accenture.demobookify.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{

    private BookRepository bookRepository;
    @Autowired
    public BookServiceImpl(BookRepository bookRepository){
        this.bookRepository=bookRepository;
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> getById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> getByAuthorId(Long id){
        return bookRepository.findByAuthorId(id);
    }

    @Override
    public Long save(Book book) {
        Book bookRes = bookRepository.save(book);
        return bookRes.getId();
    }

    @Override
    public Long update(Long id, Book book) {
        Optional<Book> bookOpt = getById(id);
        if(bookOpt.isPresent()){
            Book bookBD = bookOpt.get();
            bookBD.setTitle(book.getTitle());
            bookBD.setAuthor_id(book.getAuthor_id());
            bookBD.setDescription(book.getDescription());
            bookBD.setDescription(book.getDescription());
            bookBD.setQuantity_available(book.getQuantity_available());
            book.setActive(book.isActive());
            bookRepository.save(bookBD);
            return bookBD.getId();
        }
        return 0L;
    }

    @Override
    public void delete(Long id) {
        Optional<Book> bookOpt = getById(id);
        if (bookOpt.isPresent()){
            Book bookBD = bookOpt.get();
            bookBD.setActive(false);
            bookRepository.save(bookBD);
        }
    }

    @Override
    public void physicalDelete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void deleteByIdAuthorId(Long id){
        bookRepository.deleteByAuthorId(id);
    }
}
