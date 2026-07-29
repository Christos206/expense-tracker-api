package com.example.ExpenseAPI.service;

import com.example.ExpenseAPI.dto.*;
import com.example.ExpenseAPI.exception.ResourceNotFoundException;
import com.example.ExpenseAPI.model.Expense;
import com.example.ExpenseAPI.model.ExpenseCategory;
import com.example.ExpenseAPI.model.User;
import com.example.ExpenseAPI.repository.ExpenseRepository;

import com.example.ExpenseAPI.repository.UserRepository;
import com.example.ExpenseAPI.specification.ExpensesSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    public List<ExpenseResponseDto> getAllExpenses() {
        User user = getCurrentUser();

        return expenseRepository.findByUser(user).stream().map(this::mapToResponseDto).toList();
    }

    public ExpenseResponseDto createExpense(ExpenseRequestDto requestDto) {

        User user = getCurrentUser();
        Expense expense = new Expense();

        expense.setTitle(requestDto.getTitle());
        expense.setAmount(requestDto.getAmount());
        expense.setCategory(requestDto.getCategory());
        expense.setUser(user);
        expense.setDate(requestDto.getDate());


        Expense savedExpense = expenseRepository.save(expense);

        return mapToResponseDto(savedExpense);
    }

    public ExpenseResponseDto getExpenseById(Long id) {
        User user = getCurrentUser();

        Expense expense = expenseRepository.findByIdAndUser(id, user).orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        return mapToResponseDto(expense);
    }

    public void deleteExpense(Long id) {
        User user = getCurrentUser();
        Expense expense = expenseRepository.findByIdAndUser(id, user).orElseThrow(() -> new ResourceNotFoundException("Expense not found"));
        expenseRepository.delete(expense);
    }

    public ExpenseResponseDto updateExpense(Long id, ExpenseUpdateDto updatedExpense) {

        User user = getCurrentUser();

        Expense expense = expenseRepository.findByIdAndUser(id, user).orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        expense.setTitle(updatedExpense.getTitle());
        expense.setAmount(updatedExpense.getAmount());
        expense.setCategory(updatedExpense.getCategory());

        Expense savedExpense = expenseRepository.save(expense);

        return mapToResponseDto(savedExpense);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        return userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public List<ExpenseResponseDto> getExpensesByCategory(ExpenseCategory category) {
        User user = getCurrentUser();

        return expenseRepository.findByUserAndCategory(user, category);
    }

    private ExpenseResponseDto mapToResponseDto(Expense expense) {

        return new ExpenseResponseDto(expense.getId(), expense.getTitle(), expense.getAmount(), expense.getCategory(), expense.getDate());
    }

    public List<ExpenseResponseDto> getExpensesByDateRange(LocalDate startDate, LocalDate endDate) {
        User user = getCurrentUser();

        return expenseRepository.findByUserAndDateBetween(user, startDate, endDate).stream().map(this::mapToResponseDto).toList();
    }

    public ExpenseSummaryDto getExpensesSummary() {
        User user = getCurrentUser();

        List<Expense> expenses = expenseRepository.findByUser(user);

        ExpenseSummaryDto summary = new ExpenseSummaryDto();

        double total = expenses.stream().mapToDouble(Expense::getAmount).sum();
        double average = total == 0 ? 0 : total / expenses.size();
        double highest = expenses.stream().mapToDouble(Expense::getAmount).max().orElse(0);
        long num = expenses.size();

        summary.setTotalExpenses(total);
        summary.setAverageExpense(average);
        summary.setHighestExpense(highest);
        summary.setNumberOfExpenses(num);

        return summary;

    }

    public ExpensePageResponseDto getExpenses(ExpenseCategory category, LocalDate from, LocalDate to, int page, int size, String sortBy, String direction) {
        User user = getCurrentUser();

        Sort sort;

        List<String> allowedSortFields = List.of("id", "title", "amount", "category", "date");

        if (!allowedSortFields.contains(sortBy)) {
            throw new IllegalArgumentException("Invalid sort field");
        }

        Specification<Expense> spec = Specification.where(ExpensesSpecification.hasUser(user));

        if (category != null) {
            spec = spec.and(ExpensesSpecification.hasCategory(category));
        }

        if (from != null && to != null) {
            spec = spec.and(ExpensesSpecification.betweenDates(from, to));
        }

        if(direction.equalsIgnoreCase("desc")){
            sort = Sort.by(sortBy).descending();
        }
        else {
            sort = Sort.by(sortBy).ascending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Expense> resp = expenseRepository.findAll(spec, pageable);

        List<ExpenseResponseDto> content = resp.getContent().stream().map(this::mapToResponseDto).toList();

        return new ExpensePageResponseDto(content, resp.getNumber(), resp.getSize(), resp.getTotalElements(), resp.getTotalPages(), resp.isFirst(), resp.isLast());
    }
}
