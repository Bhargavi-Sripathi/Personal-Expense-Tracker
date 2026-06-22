package com.expensetracker.expense_tracker.dto;

public class MonthlyExpenseDTO {

    private Integer month;
    private Double totalAmount;

    public MonthlyExpenseDTO(Integer month,
                             Double totalAmount) {
        this.month = month;
        this.totalAmount = totalAmount;
    }

    public Integer getMonth() {
        return month;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }
}