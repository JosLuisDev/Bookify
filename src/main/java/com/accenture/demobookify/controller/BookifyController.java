package com.accenture.demobookify.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class BookifyController {

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id) {
        return String.format("Bienvenido al end point findById, ingresaste el id %s", id);
    }

    @GetMapping("/")
    public String findAll() {
        return "Aqui se deberian listar todos los libros.. O si eres como yo mangas 😛";
    }

    @PostMapping("/")
    String save(@RequestBody String obj){
        return String.format("En esta peticion http tiene un body..." +
                "%s", obj);
    }

    @PutMapping("/{id}")
    String update (@PathVariable Integer id){
        return "Aqui deberiamos buscar primero que el registro exista antes de editarlo...";
    }

    @DeleteMapping("/{id}")
    String delete(@PathVariable Integer id){
        return "Este deberia ser el borrado logico de la base de datos... en realidad no se elimina 🧐";
    }

    @DeleteMapping("/eliminar/{id}")
    String physicalDelete(@PathVariable Integer id){
        return "Aqui deberiamos hacer un DELETE sin un WHERE... 🫨";
    }
}
