package com.example.ExpenseAPI.repository;

import com.example.ExpenseAPI.dto.ExpenseResponseDto;
import com.example.ExpenseAPI.model.Expense;
import com.example.ExpenseAPI.model.ExpenseCategory;
import com.example.ExpenseAPI.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository  extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);
    Optional<Expense> findByIdAndUser(Long id, User user);
    List<ExpenseResponseDto> findByUserAndCategory(User user, ExpenseCategory category);
    List<Expense> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
}
