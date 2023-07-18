package com.accenture.demobookify.service;

import com.accenture.demobookify.dto.DatosBook;
import com.accenture.demobookify.dto.DatosBookResponse;
import com.accenture.demobookify.exception.ArgumentInvalidException;
import com.accenture.demobookify.exception.DataNotFoundException;
import com.accenture.demobookify.model.Author;
import com.accenture.demobookify.model.Book;
import com.accenture.demobookify.model.Order;
import com.accenture.demobookify.repository.AuthorRepository;
import com.accenture.demobookify.repository.BookRepository;
import com.accenture.demobookify.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final OrderRepository orderRepository;
    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, OrderRepository orderRepository){
        this.bookRepository=bookRepository;
        this.authorRepository = authorRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<DatosBookResponse> getAll() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(DatosBookResponse::new).toList();
    }

    @Override
    public DatosBookResponse getById(Long id) throws DataNotFoundException{
        Optional<Book> book =  bookRepository.findById(id);
        if (book.isEmpty()) throw new DataNotFoundException("The book with id " + id + " not exist");
        return new DatosBookResponse(book.get());
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
    public DatosBookResponse update(Long id, DatosBook datosBook) throws ArgumentInvalidException, DataNotFoundException{
        if(!isInteger(datosBook.quantity_available())){
            throw new ArgumentInvalidException("quantity_available must be a positive integer value");
        }
        Optional<Author> author = authorRepository.findById(datosBook.authorId());
        if (author.isEmpty()) throw new DataNotFoundException("The author id " + datosBook.authorId() + " not exist");
        Optional<Book> bookOpt = bookRepository.findById(id);
        if (bookOpt.isEmpty()) throw new DataNotFoundException("The book id " + id + " not exist");
        Book book = bookOpt.get();
        book.setTitle(datosBook.title());
        book.setAuthor(author.get());
        book.setDescription(datosBook.description());
        book.setPrice(datosBook.price());
        book.setQuantity_available((int) datosBook.quantity_available());
        book.setPublicationYear(datosBook.publicationYear());
        book.setLanguage(datosBook.language());
        bookRepository.save(book);
        return new DatosBookResponse(book);
    }

//    @Override
//    public DatosBookResponse delete(Long id) {
//        Book bookBD = bookRepository.getReferenceById(id);
//        bookBD.setActive(false);
//        return new DatosBookResponse(bookBD);
//    }

    @Override
    public void physicalDelete(Long id) throws DataNotFoundException{
        this.getById(id);//Validar que exista el book en BD
        List<Order> ordersWithThisBook = orderRepository.findByBooksId(id); //Obtener las ordenes asociadas con el libro
        //Validar que existan ordenes
        if(!ordersWithThisBook.isEmpty()){
            ordersWithThisBook.forEach(order -> {//Eliminar cada order asociada al libro para que nos deje eliminar el libro
                orderRepository.deleteById(order.getId());
            });
        }
        bookRepository.deleteById(id);//Eliminar el libro
    }

    @Override
    public void deleteByIdAuthorId(Long id){
        bookRepository.deleteByAuthorId(id);
    }

    private boolean isInteger(double quantity){
        return (Math.floor(quantity) == quantity);
    }
}
