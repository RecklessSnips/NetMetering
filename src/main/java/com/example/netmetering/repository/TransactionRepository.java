package com.example.netmetering.repository;

import com.example.netmetering.entities.Transaction;
import org.springframework.data.repository.CrudRepository;


public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
}
