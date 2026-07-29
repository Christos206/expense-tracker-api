package com.example.demo.repository;

import com.example.demo.dto.ExpenseResponseDto;
import com.example.demo.model.Expense;
import com.example.demo.model.ExpenseCategory;
import com.example.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository  extends JpaRepository<Expense, Long>, JpaSpecificationExecutor<Expense> {

    List<Expense> findByUser(User user);
    Optional<Expense> findByIdAndUser(Long id, User user);
    List<ExpenseResponseDto> findByUserAndCategory(User user, ExpenseCategory category);
    List<Expense> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
    Page<Expense> findByUser(User user, Pageable pageable);
}
