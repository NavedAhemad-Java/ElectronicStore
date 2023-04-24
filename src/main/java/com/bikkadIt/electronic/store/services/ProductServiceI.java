package com.bikkadIt.electronic.store.services;

import com.bikkadIt.electronic.store.dtos.PagebaleResponse;
import com.bikkadIt.electronic.store.dtos.ProductDto;

import java.util.List;

public interface ProductServiceI {
    //create product
    ProductDto createProduct(ProductDto productDto);

    //update Product

    ProductDto updateProduct(ProductDto productDto,Long productId);

    //getSingle Product

    ProductDto getSingleProduct(Long productId);

    //getAll Product

    PagebaleResponse<ProductDto>getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //delete product

    void deleteProduct(Long productId);

    //get All:live

    PagebaleResponse<ProductDto> getAllLive(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //search product
    PagebaleResponse<ProductDto>searchByTitle(String subTitle,Integer pageNumber, Integer pageSize, String sortBy, String sortDir);


   //create produt wit category

    ProductDto createWithCategory(ProductDto productDto,Long categoryId);

    ProductDto updateCateogry(Long productId,Long categoryId);


    PagebaleResponse<ProductDto>getAllOfCategory(Long categoryId,Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
}
