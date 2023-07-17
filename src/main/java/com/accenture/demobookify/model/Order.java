package com.accenture.demobookify.model;

import com.accenture.demobookify.dto.DatosOrder;
import com.accenture.demobookify.util.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id") //La tabla order tendra el id de la tabla user_bookify
    private User user;

    /*
    Dado que una orden puede tener muchos libros y un libro puede estar en muchas ordenes, se requiere una relacion de muchos a muchos y que genere una tabla de relacion
    entre estas tablas (orders y books)
     */
    @ManyToMany
    @JoinTable(
            name = "order_book", // Esto creara una nueva tabla de relacion llamada order_book en BD
            joinColumns = @JoinColumn(name = "order_id"), //Definimos las columnas de union
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books;

    @Column(name = "shipping_address")
    private String shippingAddress;

    private OrderStatus status;
    @Column(name = "shipping_date")
    private LocalDate shippingDate;

    @Column(name = "total_quantity_books")
    private int totalQuantityBooks;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    public Order(DatosOrder datosOrder) {
        this.shippingAddress = datosOrder.shippingAddress();
        this.status = datosOrder.status();
    }

}
