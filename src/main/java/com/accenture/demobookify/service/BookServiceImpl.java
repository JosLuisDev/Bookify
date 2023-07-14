package com.accenture.demobookify.service;

import com.accenture.demobookify.dto.DatosBook;
import com.accenture.demobookify.dto.DatosBookResponse;
import com.accenture.demobookify.exception.ArgumentInvalidException;
import com.accenture.demobookify.model.Author;
import com.accenture.demobookify.model.Book;
import com.accenture.demobookify.repository.AuthorRepository;
import com.accenture.demobookify.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository){
        this.bookRepository=bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<DatosBookResponse> getAll() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(DatosBookResponse::new).toList();
    }

    @Override
    public DatosBookResponse getById(Long id) {
        Book book =  bookRepository.getReferenceById(id);
        return new DatosBookResponse(book);
    }
    @Override
    public List<DatosBookResponse> getBooksByAuthor(Long id){
        List<Book> booksDb = bookRepository.findByAuthorId(id);
        return booksDb.stream().map(DatosBookResponse::new).toList();
    }

    @Override
    public List<Book> getByAuthorId(Long id){
        return bookRepository.findByAuthorId(id);
    }

    @Override
    public DatosBookResponse save(DatosBook datosBook) throws ArgumentInvalidException{
        if(!isInteger(datosBook.quantity_available())){
            throw new ArgumentInvalidException("quantity_available must be a positive integer value");
        }
        Author author = authorRepository.getReferenceById(datosBook.authorId());
        Book book = bookRepository.save(new Book(datosBook, author));
        return new DatosBookResponse(book);
    }

    @Override
    public DatosBookResponse update(Long id, DatosBook datosBook) throws ArgumentInvalidException{
        if(!isInteger(datosBook.quantity_available())){
            throw new ArgumentInvalidException("quantity_available must be a positive integer value");
        }
        Author author = authorRepository.getReferenceById(datosBook.authorId());
        Book book = bookRepository.getReferenceById(id);
        book.setTitle(datosBook.title());
        book.setAuthor(author);
        book.setDescription(datosBook.description());
        book.setPrice(datosBook.price());
        book.setQuantity_available((int) datosBook.quantity_available());
        book.setPublicationYear(datosBook.publicationYear());
        book.setLanguage(datosBook.language());
        return new DatosBookResponse(book);
    }

    @Override
    public DatosBookResponse delete(Long id) {
        Book bookBD = bookRepository.getReferenceById(id);
        bookBD.setActive(false);
        return new DatosBookResponse(bookBD);
    }

    @Override
    public void physicalDelete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void deleteByIdAuthorId(Long id){
        bookRepository.deleteByAuthorId(id);
    }

    private boolean isInteger(double quantity){
        return (Math.floor(quantity) == quantity);
    }
}
