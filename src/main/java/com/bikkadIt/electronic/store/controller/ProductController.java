package com.bikkadIt.electronic.store.controller;

import com.bikkadIt.electronic.store.dtos.PagebaleResponse;
import com.bikkadIt.electronic.store.dtos.ProductDto;
import com.bikkadIt.electronic.store.dtos.UserDto;
import com.bikkadIt.electronic.store.helper.ApiUserResponse;
import com.bikkadIt.electronic.store.helper.AppConstance;
import com.bikkadIt.electronic.store.helper.ImageResponse;
import com.bikkadIt.electronic.store.services.FileService;
import com.bikkadIt.electronic.store.services.ProductServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductServiceI productServiceI;
    @Autowired
    private FileService fileService;

    @Value("${product.image.path}")
    private String imagePath;

    //create product

    /**
     *
     * @param productDto
     * @return
     */
    @PostMapping("/createProduct")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        log.info("Initiating request for save product");
        ProductDto product = this.productServiceI.createProduct(productDto);
        log.info("Completed request for save product");
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    //updateProduct

    /**
     *
     * @param productDto
     * @param productId
     * @return
     */
    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable Long productId) {
        log.info("Initiating request for update product with:{}", productId);
        ProductDto update = this.productServiceI.updateProduct(productDto, productId);
        log.info("Completed request for update product with:{}", productId);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    /**
     *
     * @param productId
     * @return
     */

    //get Single Product
    @GetMapping("/singleProduct/{productId}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable Long productId) {
        log.info("Initiating request for getSingle Product  with:{}", productId);
        ProductDto singleProduct = this.productServiceI.getSingleProduct(productId);
        log.info("Completed request for getSing product with:{}", productId);
        return new ResponseEntity<>(singleProduct, HttpStatus.OK);
    }

    /**
     *
     * @param pageNumber
     * @param PageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    //GetAll Proudct

    @GetMapping("/allProduct")
    public ResponseEntity<PagebaleResponse> getAllProduct(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer PageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sorDir", defaultValue = "desc", required = false) String sortDir) {
        log.info("Initiating request for getAll product ");
        PagebaleResponse<ProductDto> allProduct = this.productServiceI.getAllProduct(pageNumber, PageSize, sortBy, sortDir);
        log.info("Completed request for getAll product ");
        return new ResponseEntity<>(allProduct, HttpStatus.OK);

    }

    /**
     *
     * @param pageNumber
     * @param PageSize
     * @param sortBy
     * @param sortDir
     * @return
     */

    //live
    @GetMapping("/liveProduct")
    public ResponseEntity<PagebaleResponse> getAllLive(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer PageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sorDir", defaultValue = "desc", required = false) String sortDir) {
        log.info("Initiating request for getAll Live product ");
        PagebaleResponse<ProductDto> allLive = this.productServiceI.getAllLive(pageNumber, PageSize, sortBy, sortDir);
        log.info("Completed request for getAll Live product ");
        return new ResponseEntity<>(allLive, HttpStatus.OK);

    }

    /**
     *
     * @param query
     * @param pageNumber
     * @param PageSize
     * @param sortBy
     * @param sortDir
     * @return
     */

    //serach
    @GetMapping("/searchProduct/{query}")
    public ResponseEntity<PagebaleResponse> getAllSearch(
            @PathVariable String query,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer PageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sorDir", defaultValue = "desc", required = false) String sortDir) {
        log.info("Initiating request for getAll search product ");
        PagebaleResponse<ProductDto> allLive = this.productServiceI.searchByTitle(query, pageNumber, PageSize, sortBy, sortDir);
        log.info("Completed request for getAll search product ");
        return new ResponseEntity<>(allLive, HttpStatus.OK);
    }

    /**
     *
     * @param productId
     * @return
     */

    //delete
    @DeleteMapping("/delete/{productId}")
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

    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse>uploadProductImage(
            @PathVariable Long productId,
            @RequestParam("productImage")MultipartFile image
            ) throws IOException {
        String fileName = this.fileService.uplodImage(image, imagePath);
        ProductDto prodDto = productServiceI.getSingleProduct(productId);
        prodDto.setProductImage(fileName);
        ProductDto updateProducts = productServiceI.updateProduct(prodDto, productId);
        ImageResponse response = ImageResponse.builder().imageName(updateProducts.getProductImage()).message("Product image successfully uploaded").success(true).status(HttpStatus.CREATED).build();
        return new ResponseEntity<>(response,HttpStatus.CREATED);

    }

    @GetMapping("/image/{productId}")
    public void serverUserImage(@PathVariable Long productId, HttpServletResponse response) throws IOException {
        ProductDto singleProduct = this.productServiceI.getSingleProduct(productId);
        InputStream resource = this.fileService.getResource(imagePath, singleProduct.getProductImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }

    //creeate product with category

    }

