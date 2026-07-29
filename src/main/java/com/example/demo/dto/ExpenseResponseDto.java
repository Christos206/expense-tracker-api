package com.example.demo.dto;

import com.example.demo.model.ExpenseCategory;

import java.time.LocalDate;

public class ExpenseResponseDto {

    private Long id;

    private String title;

    private double amount;

    private ExpenseCategory category;

    private LocalDate date;

    public ExpenseResponseDto() {
    }

    public ExpenseResponseDto(Long id, String title, double amount, ExpenseCategory category, LocalDate date) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public ExpenseCategory getCategory() {
        return category;
    }

    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
