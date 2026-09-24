package com.example.ExpenseAPI.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private double amount;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private ExpenseCategory category;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Expense() {
    }

    public Expense(Long id, String title, double amount, LocalDate date) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.date = date;
    }
}