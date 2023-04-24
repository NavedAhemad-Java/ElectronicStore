package com.bikkadIt.electronic.store.service;


import com.bikkadIt.electronic.store.BaseTest;
import com.bikkadIt.electronic.store.dtos.PagebaleResponse;
import com.bikkadIt.electronic.store.dtos.ProductDto;
import com.bikkadIt.electronic.store.entities.Product;
import com.bikkadIt.electronic.store.respository.ProductRepository;
import com.bikkadIt.electronic.store.services.ProductServiceI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductServiceTest extends BaseTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductServiceI productServiceI;

    @Autowired
    private ModelMapper modelMapper;

    Product product = Product.builder()
            .title("LCD TV")
            .description("realme 36 cm and sound bast quality")
            .price(23645.25)
            .quantity(1)
            .discountPrice(5.00)
            .stock(true)
            .live(true)
            .build();

    @BeforeEach
    public void init() {

        product = Product.builder()
                .title("LCD TV")
                .description("realme 36 cm and sound bast quality")
                .price(23645.25)
                .quantity(1)
                .discountPrice(5.00)
                .stock(true)
                .live(true)
                .build();

    }

    //create product test
    @Test
    public void createProductTest() {
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        ProductDto product1 = productServiceI.createProduct(modelMapper.map(product, ProductDto.class));
        System.out.println(product1.getTitle());
        Assertions.assertNotNull(product1);
        Assertions.assertEquals("LCD TV", product1.getTitle());

    }

    //update product test
    @Test
    public void updateProductTest() {
        Long productId = 1L;
        ProductDto dto = ProductDto.builder()
                .title("LCD TV")
                .description("realme 36 cm and sound bast quality")
                .price(23645.25)
                .quantity(1)
                .discountPrice(5.00)
                .stock(true)
                .live(true)
                .build();

        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        ProductDto update = productServiceI.updateProduct(dto, productId);
        System.out.println(update.getTitle());
        System.out.println(update.getPrice());
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(dto.getTitle(), update.getTitle(), "Category not found");

    }

    @Test
    public void deleteProductTest() {
        Long productId = 1L;
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product));
        productServiceI.deleteProduct(productId);
        Mockito.verify(productRepository, Mockito.timeout(1)).delete(product);


    }

    @Test
    public void getAllProductTest() {
        Product product1 = Product.builder()
                .title("LCD TV")
                .description("realme 36 cm and sound bast quality")
                .price(23645.25)
                .quantity(1)
                .discountPrice(5.00)
                .stock(true)
                .live(true)
                .build();
        Product product2 = Product.builder()
                .title("LCD TV")
                .description("realme 36 cm and sound bast quality")
                .price(23645.25)
                .quantity(1)
                .discountPrice(5.00)
                .stock(true)
                .live(true)
                .build();
        Product product3 = Product.builder()
                .title("LCD TV")
                .description("realme 36 cm and sound bast quality")
                .price(23645.25)
                .quantity(1)
                .discountPrice(5.00)
                .stock(true)
                .live(true)
                .build();


        List<Product> products = Arrays.asList(product, product1, product2, product3);
        PageImpl<Product> products1 = new PageImpl<>(products);
        Mockito.when(productRepository.findAll((Pageable) Mockito.any())).thenReturn(products1);
        PagebaleResponse<ProductDto> allProduct = productServiceI.getAllProduct(1, 2, "name", "asc");
        Assertions.assertEquals(4, allProduct.getContent().size());
    }

    @Test
    public void getSingleProductTest() {
        Long productId = 1L;
        ProductDto dto = ProductDto.builder()
                .title("LCD TV")
                .description("realme 36 cm and sound bast quality")
                .price(23645.25)
                .quantity(1)
                .discountPrice(5.00)
                .stock(true)
                .live(true)
                .build();

        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product));
        ProductDto singleProduct = productServiceI.getSingleProduct(productId);
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(dto.getProductId(), singleProduct.getProductId());
    }

//    @Test
//    public void searchByTitleTest() {
//
//        Product product1 = Product.builder()
//                .title("LCD TV")
//                .description("realme 36 cm and sound bast quality")
//                .price(23645.25)
//                .quantity(1)
//                .discountPrice(5.00)
//                .stock(true)
//                .live(true)
//                .build();
//
//
//        Product product2 = Product.builder()
//                .title("LCD TV")
//                .description("realme 36 cm and sound bast quality")
//                .price(23645.25)
//                .quantity(1)
//                .discountPrice(5.00)
//                .stock(true)
//                .live(true)
//                .build();
//
//
//        Product product3 = Product.builder()
//                .title("LCD TV")
//                .description("realme 36 cm and sound bast quality")
//                .price(23645.25)
//                .quantity(1)
//                .discountPrice(5.00)
//                .stock(true)
//                .live(true)
//                .build();
//        String subTitle = "LCD TV";
//        Page<Product> products = (Page<Product>) Arrays.asList(product, product1, product2, product3);
//        PageImpl<Product> products1 = new PageImpl<>(products);
//        Mockito.when(this.productRepository.findByTitleContaining(subTitle, (Pageable) products)).thenReturn(products1);
//        PagebaleResponse<ProductDto> allProduct = productServiceI.searchByTitle(subTitle, 1, 2, "name", "asc");
//        Assertions.assertEquals(4, allProduct.getContent().size());
//
//
//
//    }
}
