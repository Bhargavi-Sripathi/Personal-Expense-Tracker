package com.expensetracker.expense_tracker.dto;

public class CategorySummaryDTO {

    private String categoryName;
    private Double totalAmount;

    public CategorySummaryDTO(String categoryName, Double totalAmount) {
        this.categoryName = categoryName;
        this.totalAmount = totalAmount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }
}