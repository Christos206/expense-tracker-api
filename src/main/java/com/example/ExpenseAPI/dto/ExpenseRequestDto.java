package com.example.ExpenseAPI.dto;

import com.example.ExpenseAPI.model.ExpenseCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class ExpenseRequestDto {
    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private Double amount;

    private ExpenseCategory category;

    private LocalDate date;

    public ExpenseRequestDto() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ExpenseCategory getCategory() {
        return category;
    }

    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
