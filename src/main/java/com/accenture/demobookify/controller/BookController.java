package com.accenture.demobookify.controller;

import com.accenture.demobookify.dto.DatosBook;
import com.accenture.demobookify.dto.DatosBookResponse;
import com.accenture.demobookify.model.Book;
import com.accenture.demobookify.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;
    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
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
//si le mando el campo id en la peticion http no lo toma en cuenta y no da error 🫣
    @PostMapping("/")
    @Transactional
    public ResponseEntity<DatosBookResponse> save(@Valid @RequestBody DatosBook datosBook, UriComponentsBuilder uriComponentsBuilder){

        DatosBookResponse book = bookService.save(datosBook);
        URI uri = uriComponentsBuilder.path("/{id}").buildAndExpand(book.id()).toUri();

        return ResponseEntity.created(uri).body(book);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosBookResponse> update(@PathVariable Long id, @Valid @RequestBody DatosBook datosBook){
        return ResponseEntity.ok(bookService.update(id, datosBook));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosBookResponse> logicalDelete(@PathVariable Long id){
        DatosBookResponse responseBody = bookService.delete(id);
        return ResponseEntity.ok(responseBody);
    }

}
