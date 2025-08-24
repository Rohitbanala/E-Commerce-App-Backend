package com.ecommerce.project.service;

import com.ecommerce.project.Repository.CategoryRepository;
import com.ecommerce.project.exceptionhandler.APIException;
import com.ecommerce.project.exceptionhandler.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryClassImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if(categories.isEmpty()){
            throw new APIException("No category is present");
        }
        List<CategoryDTO> categoryDTOS = categories.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        return categoryResponse;
    }

    @Override
    public void createCategory(Category category) {
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if(savedCategory!=null){
            throw new APIException("Category with name "+ category.getCategoryName()+ " already exists");
        }
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long CategoryId) {
        Category category = categoryRepository.findById(CategoryId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",CategoryId));
        categoryRepository.delete(category);
        return category.getCategoryName() + " deleted";
    }

    @Override
    public Category updateCategory(Category category,Long CategoryId) {
        Category existingCategory = categoryRepository.findById(CategoryId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",CategoryId));
        existingCategory.setCategoryName(category.getCategoryName());
        return categoryRepository.save(existingCategory);
    }
}
