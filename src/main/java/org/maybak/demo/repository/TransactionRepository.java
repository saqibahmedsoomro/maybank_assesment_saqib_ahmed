package org.maybak.demo.repository;

import org.maybak.demo.entity.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity,Long> {

    @Query("SELECT t FROM TransactionDto t " +
            "WHERE (:description IS NULL OR LOWER(t.description) LIKE LOWER(CONCAT('%', :description, '%'))) " +
            "AND (:accountNumber IS NULL OR LOWER(t.accountNumber) LIKE LOWER(CONCAT('%', :accountNumber, '%'))) " +
            "AND (:customerId IS NULL OR t.customerId = :customerId)")
    Page<TransactionEntity> search(
            @Param("description") String description,
            @Param("accountNumber") String accountNumber,
            @Param("customerId") Long customerId,
            Pageable pageable);

}
