package com.example.ExpenseAPI.dto;

import com.example.ExpenseAPI.model.ExpenseCategory;

public class ExpenseUpdateDto {
    private String title;
    private double amount;
    private ExpenseCategory category;

    public ExpenseUpdateDto(){

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

    public ExpenseCategory getCategory() {
        return category;
    }

    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }
}
