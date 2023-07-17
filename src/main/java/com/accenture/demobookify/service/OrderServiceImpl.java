package com.accenture.demobookify.service;

import com.accenture.demobookify.dto.DatosBookResponse;
import com.accenture.demobookify.dto.DatosOrder;
import com.accenture.demobookify.exception.DataNotFoundException;
import com.accenture.demobookify.model.Book;
import com.accenture.demobookify.model.Order;
import com.accenture.demobookify.model.User;
import com.accenture.demobookify.repository.BookRepository;
import com.accenture.demobookify.repository.OrderRepository;
import com.accenture.demobookify.repository.UserRepository;
import com.accenture.demobookify.util.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{

    OrderRepository orderRepository;
    UserRepository userRepository;

    BookRepository bookRepository;
    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, BookRepository bookRepository){
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order getById(Long id) throws DataNotFoundException {
        Optional<Order> orderBD = orderRepository.findById(id);
        if(orderBD.isEmpty()){
            throw new DataNotFoundException("Order with id " + id + " doesn't exist");
        }
        return orderBD.get();
    }

    @Override
    public List<Order> getOrdersByBook(Long id) throws DataNotFoundException{
        if (bookRepository.findById(id).isEmpty()) throw new DataNotFoundException("There is no book with id " + id);
        return orderRepository.findByBooksId(id);
    }

    @Override
    public List<DatosBookResponse> getBooksByOrder(Long id){
        Order order = orderRepository.findById(id).get();
        return order.getBooks().stream().map(DatosBookResponse::new).toList();
    }

    @Override
    public Order save(DatosOrder datosOrder)throws DataNotFoundException {
        User user = validateUser(datosOrder.user_id());
        List<Book> books = validateBooks(datosOrder.books_id());

        //Calcular el precio total de los libros en la orden
        BigDecimal sum = books.stream().map(Book::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);//reduce(valorInicial, reductorOperacion)

        //Setear los valores mandados por el usuario y los recuperados de User y Books
        Order order = new Order(datosOrder);
        order.setUser(user);
        order.setBooks(books);
        order.setTotalQuantityBooks(books.size());
        order.setTotalPrice(sum);
        if(order.getStatus() == OrderStatus.SHIPPED){
            if (datosOrder.shippingDate() == null) throw new RuntimeException("If status is SHIPPED the field shippingDate is required");
            order.setShippingDate(datosOrder.shippingDate());
        }
        //Persistir con ayuda del repositorio
        return orderRepository.save(order);
    }

    @Override
    public Order update(Long id, DatosOrder datosOrder) throws  DataNotFoundException{
        Optional<Order> orderOpt = orderRepository.findById(id);
        if(orderOpt.isEmpty()) throw new DataNotFoundException("The order with the id " + id + " not exist");

        Order orderDb = orderOpt.get();
        //Validar que los datos de author que vienen en la peticion no sean los mismos que ya existen en BD
        if(!Objects.equals(orderDb.getUser().getId(), datosOrder.user_id())){
            User user = validateUser(datosOrder.user_id());
            orderDb.setUser(user);
        }
        //Validar que los datos de los libros que vienen en la peticion no sean los mismos que ya existen en BD
        List<Long> bookdIsDb = orderDb.getBooks().stream().map(Book::getId).toList();
        if(!bookdIsDb.equals(datosOrder.books_id())){
            List<Book> books = validateBooks(datosOrder.books_id());
            BigDecimal sum = books.stream().map(Book::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            orderDb.setBooks(books);
            orderDb.setTotalPrice(sum);
        }
        //Si cambian el estado a SHIPPED la shippingDate se inicializara con la fecha actual
        if(datosOrder.status() == OrderStatus.SHIPPED){
            orderDb.setShippingDate(datosOrder.shippingDate());
        }

        orderDb.setShippingAddress(datosOrder.shippingAddress());
        orderDb.setStatus(datosOrder.status());

        System.out.println(orderDb);
        return orderRepository.save(orderDb);
    }

    @Override
    public void deleteById(Long id) throws DataNotFoundException{
        Optional<Order> orderOpt = orderRepository.findById(id);
        if(orderOpt.isEmpty()) throw new DataNotFoundException("The order with the id " + id + " not exist");
        Order orderBD = orderOpt.get();
        orderBD.setStatus(OrderStatus.CANCELED);
        orderRepository.save(orderBD);
    }

    private User validateUser(Long id) throws DataNotFoundException{
        //Recuperar datos del usuario
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) throw new DataNotFoundException("The author with id " + id + " not exist");
        return userOpt.get();
    }

    private List<Book> validateBooks(List<Long> books_id) throws DataNotFoundException {
        //Recuperar datos de todos los libros
        return books_id.stream().map(bookId -> {
            Optional<Book> bookOpt = bookRepository.findById(bookId);
            if (bookOpt.isEmpty()) throw new RuntimeException("The book with id " + bookId + " not exist");
            return bookOpt.get();
        }).toList();

    }
}
