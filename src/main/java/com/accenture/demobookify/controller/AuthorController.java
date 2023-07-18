package com.accenture.demobookify.controller;

import com.accenture.demobookify.dto.DatosAuthor;
import com.accenture.demobookify.dto.DatosBookResponse;
import com.accenture.demobookify.exception.AuthorDataAlreadyExistException;
import com.accenture.demobookify.exception.DataNotFoundException;
import com.accenture.demobookify.exception.UrlNotAccesibleException;
import com.accenture.demobookify.model.Author;
import com.accenture.demobookify.model.Book;
import com.accenture.demobookify.service.AuthorService;
import com.accenture.demobookify.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private AuthorService authorService;
    private BookService bookService;
    @Autowired
    public AuthorController(AuthorService authorService, BookService bookService){
        this.authorService=authorService;
        this.bookService=bookService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try{
            Author author = authorService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(author);
        }catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<Author>> findAll() {
        List<Author> authors = authorService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(authors);
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<List<DatosBookResponse>> findBooksByAuthor(@PathVariable Long id){
        return ResponseEntity.ok(bookService.getBooksByAuthor(id));
    }
    /*
    Al crear un usuario no debemos mandar el ID, puesto que es autogenerado y el campo isActive debe de ser siempre true
    cuando se crea un usuario. Para esto utilizamos el DTO DatosAuthor.
     */
    @PostMapping("/")
    @Transactional
    ResponseEntity<String> save(@Valid @RequestBody DatosAuthor datosAuthor){
        //Si no hay errores en la peticion
        Long id = -1L;
        try{
            id = authorService.save(datosAuthor);
        }catch(AuthorDataAlreadyExistException | UrlNotAccesibleException e){
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
    ResponseEntity<?> update (@PathVariable Long id, @Valid @RequestBody DatosAuthor author){
        try{
            Long idRes = authorService.update(id,author);
            return ResponseEntity.status(HttpStatus.OK).body("Se actualizo el registro con id: " + id);
        }catch (DataNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @DeleteMapping("/{id}")
//    @Transactional
//    ResponseEntity<String> delete(@PathVariable Long id){
//        try {
//            authorService.delete(id);
//            return ResponseEntity.ok("Logic delete success");
//        }catch (DataNotFoundException e){
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    @DeleteMapping("/{id}")
    @Transactional//Solucionar error: No EntityManager with actual transaction available for current thread - cannot reliably process 'remove' call] with root cause
    ResponseEntity<String> physicalDelete(@PathVariable Long id){
        try{
            //Como libro tiene relacion con purchas debemos borrar primero las compras
            List<Book> books = bookService.getByAuthorId(id);
            books.forEach(book -> {
                try {
                    bookService.physicalDelete(book.getId());
                } catch (DataNotFoundException e) {
                    throw new RuntimeException("Book does not exist in database: " + book.toString());
                }
            });
            //Como tiene una relacion el author con los libros borramos primero los libros asociados con el author
            bookService.deleteByIdAuthorId(id);
            //Ahora si borramos al author
            authorService.physicalDelete(id);
            return ResponseEntity.ok("Physical delete success");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
}
