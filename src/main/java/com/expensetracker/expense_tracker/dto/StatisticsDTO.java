package com.expensetracker.expense_tracker.dto;

public class StatisticsDTO {

    private Double highestExpense;
    private Double lowestExpense;
    private Double averageExpense;
    private String topSpendingCategory;

    public StatisticsDTO(
            Double highestExpense,
            Double lowestExpense,
            Double averageExpense,
            String topSpendingCategory) {

        this.highestExpense = highestExpense;
        this.lowestExpense = lowestExpense;
        this.averageExpense = averageExpense;
        this.topSpendingCategory = topSpendingCategory;
    }

    public Double getHighestExpense() {
        return highestExpense;
    }

    public Double getLowestExpense() {
        return lowestExpense;
    }

    public Double getAverageExpense() {
        return averageExpense;
    }

    public String getTopSpendingCategory() {
        return topSpendingCategory;
    }
}
