package com.example.ExpenseAPI.controller;

import com.example.ExpenseAPI.dto.*;
import com.example.ExpenseAPI.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    @Operation(summary = "Retrieve expenses", description = "Returns expenses for the authenticated user.")
    public List<ExpenseResponseDto> getExpenses() {
        return expenseService.getAllExpenses();
    }

    @PostMapping
    @Operation(summary = "Create expenses", description = "Creates expenses for the authenticated user.")
    public ExpenseResponseDto createExpense(@Valid @RequestBody ExpenseRequestDto requestDto) {
        return expenseService.createExpense(requestDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find an expense", description = "Finds an expense for the authenticated user with the given id.")
    public ExpenseResponseDto getExpense(@PathVariable Long id) {
        return expenseService.getExpenseById(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an expense", description = "Deletes an expense for the authenticated user with the given id.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates an expense", description = "Updates an expense for the authenticated user with the given id.")
    public ExpenseResponseDto updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseUpdateDto requestDto
    ) {
        return expenseService.updateExpense(id, requestDto);
    }

    @GetMapping("/summary")
    @Operation(summary = "Get summary", description = "Get the summary for the authenticated user.")
    public ExpenseSummaryDto getSummary(){

        return expenseService.getExpensesSummary();
    }

}
