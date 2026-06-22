package com.expensetracker.expense_tracker.dto;

public class DashboardSummaryDTO {

    private Double totalExpense;
    private Double currentMonthExpense;
    private Long totalCategories;
    private Long totalExpenses;

    public DashboardSummaryDTO(
            Double totalExpense,
            Double currentMonthExpense,
            Long totalCategories,
            Long totalExpenses) {

        this.totalExpense = totalExpense;
        this.currentMonthExpense = currentMonthExpense;
        this.totalCategories = totalCategories;
        this.totalExpenses = totalExpenses;
    }

    public Double getTotalExpense() {
        return totalExpense;
    }

    public Double getCurrentMonthExpense() {
        return currentMonthExpense;
    }

    public Long getTotalCategories() {
        return totalCategories;
    }

    public Long getTotalExpenses() {
        return totalExpenses;
    }
}