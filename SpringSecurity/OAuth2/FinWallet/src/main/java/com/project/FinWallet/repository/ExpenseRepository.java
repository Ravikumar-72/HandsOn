package com.project.FinWallet.repository;

import com.project.FinWallet.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user.id = :userId")
    Double sumByUser(@Param("userId") Long userId);

    List<Expense> findAllByUser_Id(Long userId);

    boolean existsByUser_Id(Long userId);
}
