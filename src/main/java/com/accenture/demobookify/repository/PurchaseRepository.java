package com.accenture.demobookify.repository;

import com.accenture.demobookify.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByBookId(Long id);
    void deleteByBookId(Long id);
}
