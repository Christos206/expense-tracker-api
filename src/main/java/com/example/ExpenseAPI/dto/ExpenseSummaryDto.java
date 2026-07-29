package com.example.ExpenseAPI.dto;

public class ExpenseSummaryDto {

    private double totalExpenses;
    private double averageExpense;
    private double highestExpense;
    private long numberOfExpenses;

    public ExpenseSummaryDto() {
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public double getAverageExpense() {
        return averageExpense;
    }

    public void setAverageExpense(double averageExpense) {
        this.averageExpense = averageExpense;
    }

    public double getHighestExpense() {
        return highestExpense;
    }

    public void setHighestExpense(double highestExpense) {
        this.highestExpense = highestExpense;
    }

    public long getNumberOfExpenses() {
        return numberOfExpenses;
    }

    public void setNumberOfExpenses(long numberOfExpenses) {
        this.numberOfExpenses = numberOfExpenses;
    }
}
