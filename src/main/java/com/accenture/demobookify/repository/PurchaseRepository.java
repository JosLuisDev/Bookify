package com.accenture.demobookify.repository;

import com.accenture.demobookify.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
