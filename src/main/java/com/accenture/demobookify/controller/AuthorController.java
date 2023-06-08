package com.accenture.demobookify.controller;

import com.accenture.demobookify.model.Author;
import com.accenture.demobookify.model.Book;
import com.accenture.demobookify.model.Purchase;
import com.accenture.demobookify.service.AuthorService;
import com.accenture.demobookify.service.BookService;
import com.accenture.demobookify.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorController {

    private AuthorService authorService;
    private BookService bookService;
    private PurchaseService purchaseService;
    @Autowired
    public AuthorController(AuthorService authorService, BookService bookService, PurchaseService purchaseService){
        this.authorService=authorService;
        this.bookService=bookService;
        this.purchaseService=purchaseService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> findById(@PathVariable Long id) {
        Author author = authorService.getById(id).get();
        return ResponseEntity.status(HttpStatus.OK).body(author);
    }

    @GetMapping("/")
    public ResponseEntity<List<Author>> findAll() {
        List<Author> authors = authorService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(authors);
    }

    @PostMapping("/")
    ResponseEntity<String> save(@RequestBody Author author){
        Long id = authorService.save(author);
        return ResponseEntity.status(HttpStatus.OK).body("Se guardo con exito el autor con id: " + id);
    }

    @PutMapping("/{id}")
    ResponseEntity<String> update (@PathVariable Long id, @RequestBody Author author){
        Long idRes = authorService.update(id,author);
        return ResponseEntity.status(HttpStatus.OK).body("Se actualizo el registro con id: " + idRes);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable Long id){
        authorService.delete(id);
        return ResponseEntity.ok("Logic delete success");
    }

    @DeleteMapping("/eliminar/{id}")
    @Transactional//Solucionar error: No EntityManager with actual transaction available for current thread - cannot reliably process 'remove' call] with root cause
    ResponseEntity<String> physicalDelete(@PathVariable Long id){
        //Como libro tiene relacion con purchas debemos borrar primero las compras
        List<Book> books = bookService.getByAuthorId(id);
        books.forEach(book -> {
            purchaseService.deleteByIdBookId(book.getId());
        });
        //Como tiene una relacion el author con los libros borramos primero los libros asociados con el author
        bookService.deleteByIdAuthorId(id);
        //Ahora si borramos al author
        authorService.physicalDelete(id);
        return ResponseEntity.ok("Physical delete success");
    }
}
