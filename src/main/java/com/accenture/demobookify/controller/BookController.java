package com.accenture.demobookify.controller;

import com.accenture.demobookify.dto.DatosBook;
import com.accenture.demobookify.dto.DatosBookResponse;
import com.accenture.demobookify.exception.ArgumentInvalidException;
import com.accenture.demobookify.exception.DataNotFoundException;
import com.accenture.demobookify.model.Order;
import com.accenture.demobookify.service.AuthorService;
import com.accenture.demobookify.service.BookService;
import com.accenture.demobookify.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/books")
@Validated
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final OrderService orderService;
    @Autowired
    public BookController(BookService bookService, AuthorService authorService, OrderService orderService){
        this.bookService = bookService;
        this.authorService = authorService;
        this.orderService = orderService;
    }
 //Generamos en DTO DatosBookResponse porque no se pueden serializar las entidades que utilizan Hibernate, arrojan un error: HttpMessageConversionException
    @GetMapping("/")
    public ResponseEntity<List<DatosBookResponse>> listAll(){
        return ResponseEntity.ok(bookService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try{
            return ResponseEntity.ok(bookService.getById(id));
        }catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{id}/orders")
    public ResponseEntity<?> findOrdersByBookId(@PathVariable Long id){
        try{
            List<Order> orders = orderService.getOrdersByBook(id);
            return ResponseEntity.ok(orders);
        }catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
//si le mando el campo id en la peticion http no lo toma en cuenta y no da error 🫣
    @PostMapping("/")
    @Transactional
    public ResponseEntity<?> save(@Valid @RequestBody DatosBook datosBook, UriComponentsBuilder uriComponentsBuilder){
        try{
            authorService.getById(datosBook.authorId());
            DatosBookResponse book = bookService.save(datosBook);
            URI uri = uriComponentsBuilder.path("/{id}").buildAndExpand(book.id()).toUri();
            return ResponseEntity.created(uri).body(book);
        }catch (ArgumentInvalidException | DataNotFoundException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody DatosBook datosBook){
//        return ResponseEntity.ok(bookService.update(id, datosBook));
        try{
            authorService.getById(datosBook.authorId());
            DatosBookResponse datosActualizados = bookService.update(id,datosBook);
            return ResponseEntity.ok(datosActualizados);
        }catch (ArgumentInvalidException | DataNotFoundException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            bookService.getById(id);
            bookService.physicalDelete(id);
        }catch (DataNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Se elimino el book con id " + id);
    }

}
