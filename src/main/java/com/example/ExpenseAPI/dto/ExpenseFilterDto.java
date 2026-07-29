package com.example.ExpenseAPI.dto;

import com.example.ExpenseAPI.model.ExpenseCategory;

import java.time.LocalDate;

public class ExpenseFilterDto {

    private ExpenseCategory category;

    private LocalDate from;

    private LocalDate to;

    private Integer page = 0;

    private Integer size = 10;

    private String sortBy = "date";

    private String direction = "desc";

    private Double minAmount;

}
