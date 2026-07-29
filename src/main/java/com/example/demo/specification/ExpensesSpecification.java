package com.example.demo.specification;

import com.example.demo.model.Expense;
import com.example.demo.model.ExpenseCategory;
import com.example.demo.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ExpensesSpecification {

    public static Specification<Expense> hasUser(User user) {

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user"), user);
    }

    public static Specification<Expense> hasCategory(ExpenseCategory category) {

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category"), category);
    }

    public static Specification<Expense> betweenDates(LocalDate from, LocalDate to) {

        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("date"), from, to);
    }

}
