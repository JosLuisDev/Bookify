package com.accenture.demobookify.controller;

import com.accenture.demobookify.model.Author;
import com.accenture.demobookify.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorController {

    private AuthorService authorService;
    @Autowired
    public AuthorController(AuthorService authorService){
        this.authorService=authorService;
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
    ResponseEntity<String> delete(@PathVariable Integer id){
        return ResponseEntity.ok("Logic delete success");
    }

    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<String> physicalDelete(@PathVariable Integer id){
        return ResponseEntity.ok("Physical delete success");
    }
}
