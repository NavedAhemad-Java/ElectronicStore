package com.bikkadIt.electronic.store.services.impl;

import com.bikkadIt.electronic.store.dtos.PagebaleResponse;
import com.bikkadIt.electronic.store.dtos.ProductDto;
import com.bikkadIt.electronic.store.entities.Product;
import com.bikkadIt.electronic.store.entities.User;
import com.bikkadIt.electronic.store.exception.ResourceNotFoundException;
import com.bikkadIt.electronic.store.helper.AppConstance;
import com.bikkadIt.electronic.store.helper.Helper;
import com.bikkadIt.electronic.store.respository.ProductRepository;
import com.bikkadIt.electronic.store.services.ProductServiceI;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

@Service
public class ProductImpl implements ProductServiceI {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ProductDto createProduct(ProductDto productDto) {

        Product product = this.modelMapper.map(productDto, Product.class);
        Product save = this.productRepository.save(product);
        return this.modelMapper.map(save,ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Long productId) {
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setQuantity(productDto.getQuantity());
        product.setDiscountPrice(productDto.getDiscountPrice());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.isStock());
        product.setLive(productDto.isLive());
        product.setAddedDate(productDto.getAddedDate());
        Product updateProduct = this.productRepository.save(product);
        return this.modelMapper.map(updateProduct,ProductDto.class);
    }

    @Override
    public ProductDto getSingleProduct(Long productId) {
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        return this.modelMapper.map(product,ProductDto.class)   ;
    }

    @Override
    public PagebaleResponse<ProductDto> getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending());
        PageRequest page = PageRequest.of(pageNumber, pageSize,sort);
        Page<Product> product = this.productRepository.findAll(page);
        PagebaleResponse<ProductDto> response = Helper.getPageableResponse(product, ProductDto.class);
        return response;
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        this.productRepository.delete(product);

    }

    @Override
    public PagebaleResponse<ProductDto>  getAllLive(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending());
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = this.productRepository.findByLiveTrue(pageable);
        PagebaleResponse<ProductDto> response = Helper.getPageableResponse(page, ProductDto.class);
        return response;
    }

    @Override
    public PagebaleResponse<ProductDto> searchByTitle(String subTitle,Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending());
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = this.productRepository.findByTitleContaining(subTitle, pageable);
        PagebaleResponse<ProductDto> response = Helper.getPageableResponse(page, ProductDto.class);
        return response;
    }
}
