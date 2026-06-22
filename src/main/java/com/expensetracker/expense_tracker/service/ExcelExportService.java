package com.expensetracker.expense_tracker.service;

import com.expensetracker.expense_tracker.entity.Expense;
import com.expensetracker.expense_tracker.repository.ExpenseRepository;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ExcelExportService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public byte[] exportExpensesToExcel() {

        try {

            List<Expense> expenses =
                    expenseRepository.findAll();

            XSSFWorkbook workbook =
                    new XSSFWorkbook();

            XSSFSheet sheet =
                    workbook.createSheet("Expenses");

            Row headerRow =
                    sheet.createRow(0);

            headerRow.createCell(0)
                    .setCellValue("ID");

            headerRow.createCell(1)
                    .setCellValue("Amount");

            headerRow.createCell(2)
                    .setCellValue("Date");

            headerRow.createCell(3)
                    .setCellValue("Description");

            headerRow.createCell(4)
                    .setCellValue("Category");

            int rowNum = 1;

            for (Expense expense : expenses) {

                Row row =
                        sheet.createRow(rowNum++);

                row.createCell(0)
                        .setCellValue(expense.getId());



                row.createCell(1)
                        .setCellValue(expense.getAmount());

                row.createCell(2)
                        .setCellValue(
                                expense.getExpenseDate()
                                        .toString());

                row.createCell(3)
                        .setCellValue(
                                expense.getDescription() != null
                                        ? expense.getDescription()
                                        : "");

                row.createCell(4)
                        .setCellValue(
                                expense.getCategoryName());
            }

            for (int i = 0; i < 5; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out =
                    new ByteArrayOutputStream();

            workbook.write(out);

            workbook.close();

            return out.toByteArray();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error generating Excel file");
        }
    }
}