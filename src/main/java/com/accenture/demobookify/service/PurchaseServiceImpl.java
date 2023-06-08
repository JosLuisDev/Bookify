package com.accenture.demobookify.service;

import com.accenture.demobookify.model.Purchase;
import com.accenture.demobookify.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseServiceImpl implements PurchaseService{

    private PurchaseRepository purchaseRepository;
    @Autowired
    public PurchaseServiceImpl(PurchaseRepository purchaseRepository){
        this.purchaseRepository=purchaseRepository;
    }

    @Override
    public List<Purchase> getAll() {
        return purchaseRepository.findAll();
    }

    @Override
    public Optional<Purchase> getById(Long id) {
        return purchaseRepository.findById(id);
    }

    @Override
    public List<Purchase> getByBookId(Long id){
        return purchaseRepository.findByBookId(id);
    }

    @Override
    public Long save(Purchase purchase) {
        Purchase purchaseRes = purchaseRepository.save(purchase);
        return purchaseRes.getId();
    }

    @Override
    public Long update(Long id, Purchase purchase) {
        Optional<Purchase> purchaseOpt = getById(id);
        if(purchaseOpt.isPresent()){
            Purchase purchaseBD = purchaseOpt.get();
            purchaseBD.setBook(purchase.getBook());
            purchaseBD.setUser(purchase.getUser());
            purchaseBD.setSaledate(purchase.getSaledate());
            purchaseBD.setTotalprice(purchase.getTotalprice());
            purchaseBD.setActive(purchase.isActive());
            purchaseRepository.save(purchaseBD);
            return purchaseBD.getId();
        }
        return 0L;
    }

    @Override
    public void delete(Long id) {
        Optional<Purchase> purchaseOpt = getById(id);
        if (purchaseOpt.isPresent()){
            Purchase purchaseBD = purchaseOpt.get();
            purchaseBD.setActive(false);
            purchaseRepository.save(purchaseBD);
        }
    }

    @Override
    public void physicalDelete(Long id) {
        purchaseRepository.deleteById(id);
    }

    @Override
    public void deleteByIdBookId(Long id) {
        purchaseRepository.deleteByBookId(id);
    }


}
