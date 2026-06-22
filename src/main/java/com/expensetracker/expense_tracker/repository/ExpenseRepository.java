package com.expensetracker.expense_tracker.repository;

import com.expensetracker.expense_tracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.expensetracker.expense_tracker.dto.CategorySummaryDTO;
import com.expensetracker.expense_tracker.dto.MonthlyExpenseDTO;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("SELECT SUM(e.amount) FROM Expense e")
    Double getTotalExpense();
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.categoryName = :categoryName")
    Double getTotalExpenseByCategory(@Param("categoryName") String categoryName);
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE MONTH(e.expenseDate) = :month AND YEAR(e.expenseDate) = :year")
    Double getMonthlyExpense(@Param("month") int month,
                         @Param("year") int year);
    @Query("""
       SELECT new com.expensetracker.expense_tracker.dto.CategorySummaryDTO(
           e.categoryName,
           SUM(e.amount)
       )
       FROM Expense e
       WHERE MONTH(e.expenseDate) = :month
       AND YEAR(e.expenseDate) = :year
       GROUP BY e.categoryName
       """)
    List<CategorySummaryDTO> getCategorySummaryByMonth(
        @Param("month") int month,
        @Param("year") int year);
    @Query("SELECT e FROM Expense e WHERE LOWER(e.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(e.categoryName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Expense> searchExpenses(@Param("keyword") String keyword);
    @Query("SELECT e FROM Expense e WHERE e.expenseDate BETWEEN :startDate AND :endDate")
    List<Expense> filterExpensesByDate(
        @Param("startDate") java.time.LocalDate startDate,
        @Param("endDate") java.time.LocalDate endDate);
    @Query("""
       SELECT e
       FROM Expense e
       WHERE MONTH(e.expenseDate) = MONTH(CURRENT_DATE)
       AND YEAR(e.expenseDate) = YEAR(CURRENT_DATE)
       """)
    List<Expense> getCurrentMonthExpenses();
    @Query("""
       SELECT SUM(e.amount)
       FROM Expense e
       WHERE MONTH(e.expenseDate)=MONTH(CURRENT_DATE)
       AND YEAR(e.expenseDate)=YEAR(CURRENT_DATE)
       """)
    Double getCurrentMonthTotal();
    @Query("""
       SELECT SUM(e.amount)
       FROM Expense e
       WHERE MONTH(e.expenseDate) = :month
       AND YEAR(e.expenseDate) = :year
       """)
    Double getTotalByMonthAndYear(
        @Param("month") int month,
        @Param("year") int year);
    @Query("""
       SELECT new com.expensetracker.expense_tracker.dto.CategorySummaryDTO(
           e.categoryName,
           SUM(e.amount)
       )
       FROM Expense e
       GROUP BY e.categoryName
       """)
    List<CategorySummaryDTO> getCategoryWiseExpense();
    @Query("""
       SELECT new com.expensetracker.expense_tracker.dto.MonthlyExpenseDTO(
           MONTH(e.expenseDate),
           SUM(e.amount)
       )
       FROM Expense e
       WHERE YEAR(e.expenseDate) = :year
       GROUP BY MONTH(e.expenseDate)
       ORDER BY MONTH(e.expenseDate)
       """)
List<MonthlyExpenseDTO> getMonthlyExpenseTrend(
        @Param("year") int year);
    @Query("SELECT MAX(e.amount) FROM Expense e")
Double getHighestExpense();

@Query("SELECT MIN(e.amount) FROM Expense e")
Double getLowestExpense();

@Query("SELECT AVG(e.amount) FROM Expense e")
Double getAverageExpense();
@Query(value = """
       SELECT category_name
       FROM expense
       GROUP BY category_name
       ORDER BY SUM(amount) DESC
       LIMIT 1
       """, nativeQuery = true)
String getTopSpendingCategory();
}