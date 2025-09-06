package com.ecommerce.project.controller;
import com.ecommerce.project.config.AppConsts;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class Categorycontroller {

    @Autowired
    private CategoryService categoryService;
    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name="pageNum", defaultValue = AppConsts.PAGE_NUMBER, required = false)Integer pageNumber, @RequestParam(name="pageSize", defaultValue = AppConsts.PAGE_SIZE,required = false)Integer pageSize, @RequestParam(name="sortBy", defaultValue = AppConsts.SORT_BY,required = false)String sortBy,@RequestParam(name="sortOrder", defaultValue = AppConsts.SORT_ORDER,required = false)String sortOrder
    ){
        CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(categoryResponse,HttpStatus.OK);
    }
    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategoryDTO,HttpStatus.CREATED);
    }
    @DeleteMapping("/admin/categories/{CategoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long CategoryId){
            return new ResponseEntity<>(categoryService.deleteCategory(CategoryId), HttpStatus.OK);
    }
    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,@PathVariable Long categoryId){
            CategoryDTO savedCategoryDTO = categoryService.updateCategory(categoryDTO,categoryId);
            return new ResponseEntity<>(savedCategoryDTO, HttpStatus.OK);
    }
}
