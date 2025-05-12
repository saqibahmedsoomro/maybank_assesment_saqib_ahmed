package org.maybak.demo.service;

import org.maybak.demo.entity.TransactionEntity;
import org.maybak.demo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository repository;

    public TransactionEntity update(Long id, String description) throws Exception {
        Optional<TransactionEntity> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new Exception("Transaction with id " + id + " not found.");
        }
        TransactionEntity transaction = optional.get();
        transaction.setDescription(description);
        repository.save(transaction);
        return transaction;
    }

    public Page<TransactionEntity> search(String description, String accountNumber, Long customerId, Pageable pageable) {
        return repository.search(description, accountNumber, customerId>0?customerId:null, pageable);
    }
}
