package com.accenture.demobookify.service;

import com.accenture.demobookify.model.Purchase;

import java.util.List;
import java.util.Optional;

public interface PurchaseService {
    List<Purchase> getAll();
    Optional<Purchase> getById(Long id);
    List<Purchase> getByBookId(Long id);
    Long save(Purchase purchase);
    Long update(Long id, Purchase purchase);
    void delete(Long id);
    void physicalDelete(Long id);
    void deleteByIdBookId(Long id);
}
