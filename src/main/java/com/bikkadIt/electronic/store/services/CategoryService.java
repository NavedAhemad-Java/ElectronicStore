package com.bikkadIt.electronic.store.services;

import com.bikkadIt.electronic.store.dtos.CategoryDto;
import com.bikkadIt.electronic.store.dtos.PagebaleResponse;

public interface CategoryService {

    //create
    CategoryDto create(CategoryDto categoryDto);

    //update
    CategoryDto update(CategoryDto categoryDto,Long categoryId);

    //delete
    void delete (Long categoryId);

    //get All

    PagebaleResponse<CategoryDto>getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);


    //get Single Category details

    CategoryDto getSingle(Long categoryId);
}
