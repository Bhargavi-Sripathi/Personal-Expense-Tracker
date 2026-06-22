package com.expensetracker.expense_tracker.service;

import com.expensetracker.expense_tracker.entity.Category;
import com.expensetracker.expense_tracker.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(String... args) {

        if (categoryRepository.count() == 0) {

            String[] defaultCategories = {
                    "Food",
                    "Travel",
                    "Shopping",
                    "Bills",
                    "Education",
                    "Health",
                    "Entertainment",
                    "Rent",
                    "Others"
            };

            for (String categoryName : defaultCategories) {

                Category category = new Category();
                category.setName(categoryName);
                category.setIsDefault(true);

                categoryRepository.save(category);
            }

            System.out.println("Default categories inserted successfully!");
        }
    }
}