package com.expensetracker.expense_tracker.service;

import com.expensetracker.expense_tracker.dto.CategorySummaryDTO;
import com.expensetracker.expense_tracker.dto.DashboardSummaryDTO;
import com.expensetracker.expense_tracker.dto.MonthComparisonDTO;
import com.expensetracker.expense_tracker.dto.MonthlyExpenseDTO;
import com.expensetracker.expense_tracker.entity.Expense;
import com.expensetracker.expense_tracker.exception.ResourceNotFoundException;
import com.expensetracker.expense_tracker.repository.CategoryRepository;
import com.expensetracker.expense_tracker.repository.ExpenseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.expensetracker.expense_tracker.dto.StatisticsDTO;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Expense saveExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense updateExpense(Long id, Expense updatedExpense) {

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Expense not found with id: " + id));

        expense.setAmount(updatedExpense.getAmount());
        expense.setExpenseDate(updatedExpense.getExpenseDate());
        expense.setDescription(updatedExpense.getDescription());
        expense.setCategoryName(updatedExpense.getCategoryName());

        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long id) {

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Expense not found with id: " + id));

        expenseRepository.delete(expense);
    }

    public Double getTotalExpense() {

        Double total = expenseRepository.getTotalExpense();

        return total != null ? total : 0.0;
    }

    public Double getTotalExpenseByCategory(String categoryName) {

        Double total = expenseRepository.getTotalExpenseByCategory(categoryName);

        return total != null ? total : 0.0;
    }

    public Double getMonthlyExpense(int month, int year) {

        Double total = expenseRepository.getMonthlyExpense(month, year);

        return total != null ? total : 0.0;
    }

    public List<CategorySummaryDTO> getCategorySummaryByMonth(
            int month,
            int year) {

        return expenseRepository.getCategorySummaryByMonth(month, year);
    }

    public List<Expense> searchExpenses(String keyword) {

        return expenseRepository.searchExpenses(keyword);
    }

    public List<Expense> filterExpensesByDate(
            LocalDate startDate,
            LocalDate endDate) {

        return expenseRepository.filterExpensesByDate(startDate, endDate);
    }

    public List<Expense> getCurrentMonthExpenses() {

        return expenseRepository.getCurrentMonthExpenses();
    }

    public MonthComparisonDTO getMonthComparison() {

        LocalDate now = LocalDate.now();
        LocalDate previousMonth = now.minusMonths(1);

        Double current =
                expenseRepository.getTotalByMonthAndYear(
                        now.getMonthValue(),
                        now.getYear());

        Double previous =
                expenseRepository.getTotalByMonthAndYear(
                        previousMonth.getMonthValue(),
                        previousMonth.getYear());

        return new MonthComparisonDTO(
                current != null ? current : 0.0,
                previous != null ? previous : 0.0
        );
    }

    public List<CategorySummaryDTO> getCategoryWiseExpense() {

        return expenseRepository.getCategoryWiseExpense();
    }

    public List<MonthlyExpenseDTO> getMonthlyExpenseTrend(
            int year) {

        return expenseRepository.getMonthlyExpenseTrend(year);
    }

    public DashboardSummaryDTO getDashboardSummary() {

        Double totalExpense = getTotalExpense();

        Double currentMonthExpense =
                getMonthlyExpense(
                        LocalDate.now().getMonthValue(),
                        LocalDate.now().getYear());

        Long totalCategories =
                categoryRepository.count();

        Long totalExpenses =
                expenseRepository.count();

        return new DashboardSummaryDTO(
                totalExpense,
                currentMonthExpense,
                totalCategories,
                totalExpenses
        );
    }

    public Page<Expense> getExpensesPaginated(
            int page,
            int size,
            String sortBy) {

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(sortBy));

        return expenseRepository.findAll(pageable);
    }

    public StatisticsDTO getStatistics() {

    Double highest =
            expenseRepository.getHighestExpense();

    Double lowest =
            expenseRepository.getLowestExpense();

    Double average =
            expenseRepository.getAverageExpense();

    String topCategory =
            expenseRepository.getTopSpendingCategory();

    return new StatisticsDTO(
            highest != null ? highest : 0.0,
            lowest != null ? lowest : 0.0,
            average != null ? average : 0.0,
            topCategory != null ? topCategory : "N/A"
    );
}

    public String exportExpensesToCSV() {

        List<Expense> expenses = expenseRepository.findAll();

        StringBuilder csv = new StringBuilder();

        csv.append("Id,Amount,Date,Description,Category\n");

        for (Expense expense : expenses) {

            csv.append(expense.getId()).append(",");

            csv.append(expense.getAmount()).append(",");
            csv.append(expense.getExpenseDate()).append(",");
            csv.append(expense.getDescription()).append(",");
            csv.append(expense.getCategoryName()).append("\n");
        }

        return csv.toString();
    }
}