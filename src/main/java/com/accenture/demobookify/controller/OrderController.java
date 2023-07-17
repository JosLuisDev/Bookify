package com.accenture.demobookify.controller;

import com.accenture.demobookify.dto.DatosOrder;
import com.accenture.demobookify.exception.DataNotFoundException;
import com.accenture.demobookify.model.Order;
import com.accenture.demobookify.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Order>> findAll(){
        List<Order> orders = orderService.getAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try{
            Order order = orderService.getById(id);
            return ResponseEntity.ok(order);
        }catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> save(@Valid @RequestBody DatosOrder datosOrder){
        try{
            return ResponseEntity.ok(orderService.save(datosOrder));
        }catch (DataNotFoundException | RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update (@PathVariable Long id, @Valid @RequestBody DatosOrder datosOrder) {
        try {
            return ResponseEntity.ok(orderService.update(id, datosOrder));
        }catch(DataNotFoundException | RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id) {
        try {
            orderService.deleteById(id);
            return ResponseEntity.ok("Se cancelo la orden con id " + id);
        }catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<?> getBooksByOrderIdSorted(@PathVariable Long id){
        try{
            return ResponseEntity.ok(orderService.getBooksByOrder(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("There is no order with id " + id);
        }
    }
}
