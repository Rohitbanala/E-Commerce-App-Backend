package com.ecommerce.project.service;

import com.ecommerce.project.Repository.CategoryRepository;
import com.ecommerce.project.exceptionhandler.APIException;
import com.ecommerce.project.exceptionhandler.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CategoryClassImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNum,Integer pageSize) {
        Pageable pageDetails = PageRequest.of(pageNum,pageSize);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
        List<Category> categories = categoryPage.getContent();
        if(categories.isEmpty()){
            throw new APIException("No category is present");
        }
        List<CategoryDTO> categoryDTOS = categories.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());
        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO,Category.class);
        Category existingCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if(existingCategory!=null){
            throw new APIException("Category with name "+ category.getCategoryName()+ " already exists");
        }
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory,CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long CategoryId) {
        Category category = categoryRepository.findById(CategoryId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",CategoryId));
        CategoryDTO deleteCategoryDTO = modelMapper.map(category,CategoryDTO.class);
        categoryRepository.delete(category);
        return deleteCategoryDTO;
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO,Long CategoryId) {
        Category savedCategory = categoryRepository.findById(CategoryId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",CategoryId));
        Category category = modelMapper.map(categoryDTO,Category.class);
        category.setCategoryId(CategoryId);
        savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory,CategoryDTO.class);
    }
}
