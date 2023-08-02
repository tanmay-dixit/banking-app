package com.tanmay.bankingapp.transaction.repository;

import com.tanmay.bankingapp.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    @Query(value = "SELECT COUNT(*) FROM transactions WHERE account_number = :accountNumber AND type = 'WITHDRAWAL' AND EXTRACT(MONTH FROM date_time) = EXTRACT(MONTH FROM CURRENT_DATE) AND EXTRACT(YEAR FROM date_time) = EXTRACT(YEAR FROM CURRENT_DATE)", nativeQuery = true)
    int countWithdrawalsInCurrentMonth(@Param("accountNumber") Long accountNumber);

}
