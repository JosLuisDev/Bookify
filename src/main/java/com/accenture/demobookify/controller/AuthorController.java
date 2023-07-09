package com.accenture.demobookify.controller;

import com.accenture.demobookify.dto.DatosAuthor;
import com.accenture.demobookify.exception.AuthorAlreadyExistException;
import com.accenture.demobookify.model.Author;
import com.accenture.demobookify.model.Book;
import com.accenture.demobookify.service.AuthorService;
import com.accenture.demobookify.service.BookService;
import com.accenture.demobookify.service.PurchaseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
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
        Author author = authorService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(author);
    }

    @GetMapping("/")
    public ResponseEntity<List<Author>> findAll() {
        List<Author> authors = authorService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(authors);
    }
    /*
    Al crear un usuario no debemos mandar el ID, puesto que es autogenerado y el campo isActive debe de ser siempre true
    cuando se crea un usuario. Para esto utilizamos el DTO DatosAuthor.
     */
    @PostMapping("/")
    @Transactional
    ResponseEntity<String> save(@Valid @RequestBody DatosAuthor datosAuthor, BindingResult bindingResult){
        //Si las validaciones de datosAuthor determinan que hay errores en la peticion
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            assert fieldError != null; //Que no produzca nullPointer
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();

            return ResponseEntity.badRequest().body(errorMessage);
        }
        //Si no hay errores en la peticion
        Long id = -1L;
        try{
            id = authorService.save(datosAuthor);
        }catch(AuthorAlreadyExistException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Se guardo con exito el autor con id: " + id);
    }
/*
Al actualizar el author, no se debe poder actualizar id para evitar errores inesperados dado que es autogenerado
ni tampoco el campo active se debe de poder actualizar a menos que se elimine logicamente el registro.
 */
    @PutMapping("/{id}")
    @Transactional//No hay necesidad de llamar al Repository los cambios que realiza en el service sobre la instancia se persisten en BD
    ResponseEntity<String> update (@PathVariable Long id, @Valid @RequestBody DatosAuthor author){
        Long idRes = authorService.update(id,author);
        return ResponseEntity.status(HttpStatus.OK).body("Se actualizo el registro con id: " + idRes);
    }

    @DeleteMapping("/{id}")
    @Transactional
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
