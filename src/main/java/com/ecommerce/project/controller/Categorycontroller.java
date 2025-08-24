package com.ecommerce.project.controller;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class Categorycontroller {
    private List<Category> categories = new ArrayList<>();
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(){
        CategoryResponse categoryResponse = categoryService.getAllCategories();
        return new ResponseEntity<>(categoryResponse,HttpStatus.OK);
    }
    @PostMapping("/public/categories")
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category){
        categoryService.createCategory(category);
        return new ResponseEntity<>("category added succesfully",HttpStatus.CREATED);
    }
    @DeleteMapping("/admin/categories/{CategoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long CategoryId){
            return new ResponseEntity<>(categoryService.deleteCategory(CategoryId), HttpStatus.OK);
    }
    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@Valid @RequestBody Category category,@PathVariable Long categoryId){
            Category savedCategory = categoryService.updateCategory(category,categoryId);
            return new ResponseEntity<>(savedCategory.getCategoryName(), HttpStatus.OK);
    }
}
