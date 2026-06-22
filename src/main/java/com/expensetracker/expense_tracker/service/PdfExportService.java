package com.expensetracker.expense_tracker.service;

import com.expensetracker.expense_tracker.entity.Expense;
import com.expensetracker.expense_tracker.repository.ExpenseRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfExportService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public byte[] exportExpensesToPdf() {

        try {

            List<Expense> expenses =
                    expenseRepository.findAll();

            Document document = new Document();

            ByteArrayOutputStream out =
                    new ByteArrayOutputStream();

            PdfWriter.getInstance(document, out);

            document.open();

            document.add(
                    new Paragraph(
                            "Personal Expense Tracker Report"));

            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(5);
table.setWidthPercentage(100);
            table.addCell("ID");
            
            table.addCell("Amount");
            table.addCell("Date");
            table.addCell("Description");
            table.addCell("Category");

            for (Expense expense : expenses) {

                table.addCell(
                        String.valueOf(expense.getId()));


                table.addCell(
                        String.valueOf(
                                expense.getAmount()));

                table.addCell(
                        String.valueOf(
                                expense.getExpenseDate()));

                table.addCell(
                        expense.getDescription() != null
                                ? expense.getDescription()
                                : "");

                table.addCell(
                        expense.getCategoryName());
            }

            document.add(table);

            document.close();

            return out.toByteArray();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error generating PDF");
        }
    }
}
