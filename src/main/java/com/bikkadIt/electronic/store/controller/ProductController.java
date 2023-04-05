package com.bikkadIt.electronic.store.controller;

import com.bikkadIt.electronic.store.dtos.PagebaleResponse;
import com.bikkadIt.electronic.store.dtos.ProductDto;
import com.bikkadIt.electronic.store.helper.ApiUserResponse;
import com.bikkadIt.electronic.store.helper.AppConstance;
import com.bikkadIt.electronic.store.services.ProductServiceI;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductServiceI productServiceI;

    //create product
    @PostMapping("/createProduct")
    public ResponseEntity<ProductDto> createproduct(@RequestBody ProductDto productDto) {
        log.info("Initiating request for save product");
        ProductDto product = this.productServiceI.createProduct(productDto);
        log.info("Completed request for save product");
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    //updateProduct
    @PutMapping("/updateProduct/{proudctId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable Long productId) {
        log.info("Initiating request for update product with:{}", productId);
        ProductDto update = this.productServiceI.updateProduct(productDto, productId);
        log.info("Completed request for update product with:{}", productId);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    //get Single Product
    @GetMapping("/singleProduct/{productId}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable Long productId) {
        log.info("Initiating request for getSingle Product  with:{}", productId);
        ProductDto singleProduct = this.productServiceI.getSingleProduct(productId);
        log.info("Completed request for getSing product with:{}", productId);
        return new ResponseEntity<>(singleProduct, HttpStatus.OK);
    }
    //GetAll Proudct

    @GetMapping("/allProduct")
    public ResponseEntity<PagebaleResponse> getAllProudct(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer PageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sorDir", defaultValue = "desc", required = false) String sortDir) {
        log.info("Initiating request for getAll product ");
        PagebaleResponse<ProductDto> allProduct = this.productServiceI.getAllProduct(pageNumber, PageSize, sortBy, sortDir);
        log.info("Completed request for getAll product ");
        return new ResponseEntity<>(allProduct, HttpStatus.OK);

    }

    //live
    @GetMapping("/liveProduct")
    public ResponseEntity<PagebaleResponse> getAllLive(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer PageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sorDir", defaultValue = "desc", required = false) String sortDir) {
        log.info("Initiating request for getAll Live product ");
        PagebaleResponse<ProductDto> allLive = this.productServiceI.getAllLive(pageNumber, PageSize, sortBy, sortDir);
        log.info("Completed request for getAll Live product ");
        return new ResponseEntity<>(allLive, HttpStatus.OK);

    }

    //serach
    @GetMapping("/searchProduct/query")
    public ResponseEntity<PagebaleResponse> getAllSearch(
            @PathVariable String query,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer PageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sorDir", defaultValue = "desc", required = false) String sortDir) {
        log.info("Initiating request for getAll search product ");
        PagebaleResponse<ProductDto> allLive = this.productServiceI.searchByTitle(query, pageNumber, PageSize, sortBy, sortDir);
        log.info("Completed request for getAll search product ");
        return new ResponseEntity<>(allLive, HttpStatus.OK);
    }

    //delete
    public ResponseEntity<ApiUserResponse> deleteUser(@PathVariable Long productId) {
        log.info("Initiating request for deleted product with:{}", productId);
        this.productServiceI.deleteProduct(productId);
        ApiUserResponse message = ApiUserResponse.builder()
                .message(AppConstance.USER_DELETE)
                .success(true)
                .status(HttpStatus.OK)
                .build();
        log.info("Completed request for deleted product with:{}", productId);
        return new ResponseEntity<>(message, HttpStatus.OK);

    }
}