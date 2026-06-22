package com.expensetracker.expense_tracker.dto;

public class MonthComparisonDTO {

    private Double currentMonthExpense;
    private Double previousMonthExpense;
    private Double difference;

    public MonthComparisonDTO(Double currentMonthExpense,
                              Double previousMonthExpense) {

        this.currentMonthExpense = currentMonthExpense;
        this.previousMonthExpense = previousMonthExpense;

        this.difference =
                currentMonthExpense - previousMonthExpense;
    }

    public Double getCurrentMonthExpense() {
        return currentMonthExpense;
    }

    public Double getPreviousMonthExpense() {
        return previousMonthExpense;
    }

    public Double getDifference() {
        return difference;
    }
}