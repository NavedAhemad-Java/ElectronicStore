package com.bikkadIt.electronic.store.services.impl;

import com.bikkadIt.electronic.store.dtos.PagebaleResponse;
import com.bikkadIt.electronic.store.dtos.ProductDto;
import com.bikkadIt.electronic.store.entities.Category;
import com.bikkadIt.electronic.store.entities.Product;
import com.bikkadIt.electronic.store.entities.User;
import com.bikkadIt.electronic.store.exception.ResourceNotFoundException;
import com.bikkadIt.electronic.store.helper.AppConstance;
import com.bikkadIt.electronic.store.helper.Helper;
import com.bikkadIt.electronic.store.respository.CategoryRepository;
import com.bikkadIt.electronic.store.respository.ProductRepository;
import com.bikkadIt.electronic.store.services.ProductServiceI;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ProductImpl implements ProductServiceI {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepository categoryRepository;
    @Value("${product.image.path}")
    private String imagePath;

    /**
     *
     * @param productDto
     * @return
     */
    @Override
    public ProductDto createProduct(ProductDto productDto) {
        log.info("Initiating dao call for the save the product Details");
        Product product = this.modelMapper.map(productDto, Product.class);
        product.setAddedDate(new Date());
        Product save = this.productRepository.save(product);
        log.info("Completed dao call for the save the product Details");
        return this.modelMapper.map(save,ProductDto.class);
    }

    /**
     *
     * @param productDto
     * @param productId
     * @return
     */
    @Override
    public ProductDto updateProduct(ProductDto productDto, Long productId) {
        log.info("Initiating dao call for update product details with:{}", productId);
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setQuantity(productDto.getQuantity());
        product.setDiscountPrice(productDto.getDiscountPrice());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.isStock());
        product.setLive(productDto.isLive());
        product.setAddedDate(productDto.getAddedDate());
        product.setAddedDate(new Date());
        product.setProductImage(productDto.getProductImage());
        Product updateProduct = this.productRepository.save(product);
        log.info("Complete dao call for update  product details: {}", productId);
        return this.modelMapper.map(updateProduct,ProductDto.class);
    }

    /**
     *
     * @param productId
     * @return
     */
    @Override
    public ProductDto getSingleProduct(Long productId) {
        log.info("Initiating dao call for getSingle product details:{}",productId);
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        log.info("Complete dao call for getSingle  product details: {}", productId);
        return this.modelMapper.map(product,ProductDto.class)   ;
    }

    /**
     *
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */

    @Override
    public PagebaleResponse<ProductDto> getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        log.info("Initiating dao call for getAll product");
        Sort sort = (sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending());
        PageRequest page = PageRequest.of(pageNumber, pageSize,sort);
        Page<Product> product = this.productRepository.findAll(page);
        PagebaleResponse<ProductDto> response = Helper.getPageableResponse(product, ProductDto.class);
        log.info("Completed dao call for getAll product ");
        return response;
    }

    /**
     *
     * @param productId
     */

    @Override
    public void deleteProduct(Long productId) {
        log.info("Initiating dao call for delete product with:{}",productId);
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));


        String fullpath = imagePath + product.getProductImage();
        try {
            Path path = Paths.get(fullpath);
            Files.delete(path);
        }catch (NoSuchFileException ex){
            log.info("Product image not found is folder");
            ex.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        product.setIsactive(AppConstance.NO);
        log.info("Completed dao call for deleted product with:{}",productId);
        this.productRepository.delete(product);

    }

    /**
     *
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */

    @Override
    public PagebaleResponse<ProductDto>  getAllLive(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        log.info("Initiating dao call for getAll Live product");
        Sort sort = (sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending());
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = this.productRepository.findByLiveTrue(pageable);
        PagebaleResponse<ProductDto> response = Helper.getPageableResponse(page, ProductDto.class);
        log.info("Completed dao call for getAll Live product");
        return response;
    }

    /**
     *
     * @param subTitle
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */

    @Override
    public PagebaleResponse<ProductDto> searchByTitle(String subTitle,Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        log.info("Initiating for dao call for search product");
        Sort sort = (sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending());
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = this.productRepository.findByTitleContaining(subTitle, pageable);
        PagebaleResponse<ProductDto> response = Helper.getPageableResponse(page, ProductDto.class);
       log.info("Completed for dao call for search product");
        return response;
    }

    @Override
    public ProductDto createWithCategory(ProductDto productDto, Long categoryId) {
        log.info("Initiating dao call for the save the category with{}:",categoryId);
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        Product product = this.modelMapper.map(productDto, Product.class);
        product.setAddedDate(new Date());
        product.setCategory(category);
        Product save = this.productRepository.save(product);
        log.info("Completed dao call for the save the product and category with{}",categoryId);
        return this.modelMapper.map(save,ProductDto.class);
    }

    @Override
    public ProductDto updateCateogry(Long productId, Long categoryId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "prodcuctId", productId));
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        
        product.setCategory(category);
        Product saveProduct = productRepository.save(product);
        return modelMapper.map(saveProduct,ProductDto.class);



    }

    @Override
    public PagebaleResponse<ProductDto> getAllOfCategory(Long categoryId,Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        Sort sort = (sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending());
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = this.productRepository.findByCategory(category,pageable);
        return Helper.getPageableResponse(page,ProductDto.class);

    }


}

