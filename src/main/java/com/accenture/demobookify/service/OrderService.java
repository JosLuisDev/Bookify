package com.accenture.demobookify.service;

import com.accenture.demobookify.dto.DatosBookResponse;
import com.accenture.demobookify.dto.DatosOrder;
import com.accenture.demobookify.exception.DataNotFoundException;
import com.accenture.demobookify.model.Order;

import java.util.List;


public interface OrderService {

    List<Order> getAll();
    Order getById(Long id) throws DataNotFoundException;
    List<Order> getOrdersByBook(Long id) throws DataNotFoundException;
    List<DatosBookResponse> getBooksByOrder(Long id);
    Order save(DatosOrder datosOrder) throws DataNotFoundException;
    Order update(Long id, DatosOrder datosOrder) throws DataNotFoundException;
    void deleteById(Long id) throws DataNotFoundException;

}
