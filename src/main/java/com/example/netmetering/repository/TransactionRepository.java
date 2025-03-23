package com.example.netmetering.repository;

import com.example.netmetering.entities.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

    @Query("SELECT t from Transaction t WHERE t.fromAccount.accountID = :fromAccountID")
    List<Transaction> findByFromAccountID(@Param("fromAccountID") String fromAccountID);
}
