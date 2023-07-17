package com.accenture.demobookify.controller;

import com.accenture.demobookify.dto.DatosBook;
import com.accenture.demobookify.dto.DatosBookResponse;
import com.accenture.demobookify.exception.ArgumentInvalidException;
import com.accenture.demobookify.exception.DataNotFoundException;
import com.accenture.demobookify.model.Order;
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
    private final OrderService orderService;
    @Autowired
    public BookController(BookService bookService, OrderService orderService){
        this.bookService = bookService;
        this.orderService = orderService;
    }
 //Generamos en DTO DatosBookResponse porque no se pueden serializar las entidades que utilizan Hibernate, arrojan un error: HttpMessageConversionException
    @GetMapping("/")
    public ResponseEntity<List<DatosBookResponse>> listAll(){
        return ResponseEntity.ok(bookService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosBookResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(bookService.getById(id));
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
    public ResponseEntity<String> save(@Valid @RequestBody DatosBook datosBook, UriComponentsBuilder uriComponentsBuilder){
        try{
            DatosBookResponse book = bookService.save(datosBook);
            URI uri = uriComponentsBuilder.path("/{id}").buildAndExpand(book.id()).toUri();
            return ResponseEntity.created(uri).body(book.toString());
        }catch (ArgumentInvalidException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<String> update(@PathVariable Long id, @Valid @RequestBody DatosBook datosBook){
//        return ResponseEntity.ok(bookService.update(id, datosBook));
        try{
            DatosBookResponse datosActualizados = bookService.update(id,datosBook);
            return ResponseEntity.ok(datosActualizados.toString());
        }catch (ArgumentInvalidException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosBookResponse> logicalDelete(@PathVariable Long id){
        DatosBookResponse responseBody = bookService.delete(id);
        return ResponseEntity.ok(responseBody);
    }

}
