package com.accenture.demobookify.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToOne(cascade = CascadeType.ALL)//NO AGARRA EK CASCADE ALL SIGUE SURGIENDO ERROR DE CONSTRAINT
    @JoinColumn(name = "author_id")//La tabla book tendra la primary key de la tabla author
    private Author author;
    private String description;
    private BigDecimal price;
    private int quantity_available;
    //@OneToMany(mappedBy = "book")//Nombre del atributo del objeto
    //private List<Purchase> purchases;
    private boolean isActive;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor_id() {
        return author;
    }

    public void setAuthor_id(Author author_id) {
        this.author = author_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity_available() {
        return quantity_available;
    }

    public void setQuantity_available(int quantity_available) {
        this.quantity_available = quantity_available;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
