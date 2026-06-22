package com.expensetracker.expense_tracker.service;

import com.expensetracker.expense_tracker.entity.Category;
import com.expensetracker.expense_tracker.exception.ResourceNotFoundException;
import com.expensetracker.expense_tracker.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(
            Long id,
            Category updatedCategory) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: " + id));

        category.setName(updatedCategory.getName());
        category.setIsDefault(updatedCategory.getIsDefault());

        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: " + id));

        categoryRepository.delete(category);
    }
}