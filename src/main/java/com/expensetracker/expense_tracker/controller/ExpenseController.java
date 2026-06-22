package com.expensetracker.expense_tracker.controller;

import com.expensetracker.expense_tracker.dto.CategorySummaryDTO;
import com.expensetracker.expense_tracker.dto.DashboardSummaryDTO;
import com.expensetracker.expense_tracker.dto.MonthComparisonDTO;
import com.expensetracker.expense_tracker.dto.MonthlyExpenseDTO;
import com.expensetracker.expense_tracker.entity.Expense;
import com.expensetracker.expense_tracker.service.ExpenseService;
import com.expensetracker.expense_tracker.service.PdfExportService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.expensetracker.expense_tracker.service.ExcelExportService;
import com.expensetracker.expense_tracker.dto.StatisticsDTO;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/expenses")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private PdfExportService pdfExportService;

@Autowired
private ExcelExportService excelExportService;

    @PostMapping
    public Expense addExpense(
            @Valid @RequestBody Expense expense) {

        return expenseService.saveExpense(expense);
    }

    @GetMapping
    public List<Expense> getAllExpenses() {

        return expenseService.getAllExpenses();
    }

    @GetMapping("/total")
    public Double getTotalExpense() {

        return expenseService.getTotalExpense();
    }

    @GetMapping("/category/{categoryName}")
    public Double getTotalExpenseByCategory(
            @PathVariable String categoryName) {

        return expenseService
                .getTotalExpenseByCategory(
                        categoryName);
    }

    @GetMapping("/monthly")
    public Double getMonthlyExpense(
            @RequestParam int month,
            @RequestParam int year) {

        return expenseService
                .getMonthlyExpense(
                        month,
                        year);
    }

    @GetMapping("/category-summary")
    public List<CategorySummaryDTO>
    getCategorySummaryByMonth(
            @RequestParam int month,
            @RequestParam int year) {

        return expenseService
                .getCategorySummaryByMonth(
                        month,
                        year);
    }

    @GetMapping("/search")
    public List<Expense> searchExpenses(
            @RequestParam String keyword) {

        return expenseService
                .searchExpenses(keyword);
    }

    @GetMapping("/filter")
    public List<Expense> filterExpensesByDate(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        return expenseService
                .filterExpensesByDate(
                        startDate,
                        endDate);
    }

    @GetMapping("/current-month")
    public List<Expense> getCurrentMonthExpenses() {

        return expenseService
                .getCurrentMonthExpenses();
    }

    @GetMapping("/month-comparison")
    public MonthComparisonDTO
    getMonthComparison() {

        return expenseService
                .getMonthComparison();
    }

    @GetMapping("/pie-chart")
    public List<CategorySummaryDTO>
    getPieChartData() {

        return expenseService
                .getCategoryWiseExpense();
    }

    @GetMapping("/bar-chart")
    public List<MonthlyExpenseDTO>
    getBarChartData(
            @RequestParam int year) {

        return expenseService
                .getMonthlyExpenseTrend(year);
    }

    @GetMapping("/dashboard")
    public DashboardSummaryDTO
    getDashboardSummary() {

        return expenseService
                .getDashboardSummary();
    }

    @GetMapping("/statistics")
public StatisticsDTO getStatistics() {

    return expenseService.getStatistics();
}

    @GetMapping("/paginated")
    public Page<Expense> getExpensesPaginated(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy) {

        return expenseService
                .getExpensesPaginated(
                        page,
                        size,
                        sortBy);
    }

    @GetMapping("/export/csv")
    public ResponseEntity<String>
    exportExpensesToCSV() {

        String csvData =
                expenseService
                        .exportExpensesToCSV();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=expenses.csv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(csvData);
    }

    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]>
    exportExpensesToPdf() {

        byte[] pdfData =
                pdfExportService
                        .exportExpensesToPdf();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=expenses.pdf")
                .contentType(
                        MediaType.APPLICATION_PDF)
                .body(pdfData);
    }

    @GetMapping("/export/excel")
public ResponseEntity<byte[]>
exportExpensesToExcel() {

    byte[] excelData =
            excelExportService
                    .exportExpensesToExcel();

    return ResponseEntity.ok()
            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=expenses.xlsx")
            .contentType(
                    MediaType.APPLICATION_OCTET_STREAM)
            .body(excelData);
}

    @PutMapping("/{id}")
    public Expense updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody Expense expense) {

        return expenseService
                .updateExpense(id, expense);
    }

    @DeleteMapping("/{id}")
    public String deleteExpense(
            @PathVariable Long id) {

        expenseService.deleteExpense(id);

        return "Expense deleted successfully";
    }
}