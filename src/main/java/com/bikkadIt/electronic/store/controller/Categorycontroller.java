package com.bikkadIt.electronic.store.controller;

import com.bikkadIt.electronic.store.dtos.CategoryDto;
import com.bikkadIt.electronic.store.dtos.PagebaleResponse;
import com.bikkadIt.electronic.store.dtos.UserDto;
import com.bikkadIt.electronic.store.helper.ApiUserResponse;
import com.bikkadIt.electronic.store.helper.AppConstance;
import com.bikkadIt.electronic.store.helper.ImageResponse;
import com.bikkadIt.electronic.store.services.CategoryService;
import com.bikkadIt.electronic.store.services.CoverService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cat/")
public class Categorycontroller {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CoverService coverService;

    @Value("${user.profile.image.path}")
    private String coverImage;


    @PostMapping("/create")
    public ResponseEntity<CategoryDto>createCat(@RequestBody CategoryDto categoryDto){
        log.info("Initiating request for save category");
        CategoryDto create = this.categoryService.create(categoryDto);
        log.info("Completed request for save category");
        return new ResponseEntity<>(create, HttpStatus.CREATED);

    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDto>updateCat(@RequestBody CategoryDto categoryDto, @PathVariable Long categoryId){
        log.info("Initiating request for update user with:{}",categoryId);
        CategoryDto update = this.categoryService.update(categoryDto, categoryId);
        log.info("Completed request for update user with:{}",categoryId);
        return new ResponseEntity<>(update,HttpStatus.OK);

    }
    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ApiUserResponse>getId(@PathVariable Long categoryId){
        log.info("Initiating request for deleted user with:{}",categoryId);
        this.categoryService.delete(categoryId);
        ApiUserResponse apiUserResponse=ApiUserResponse.builder()
                                        .message(AppConstance.USER_DELETE)
                                        .success(true)
                                        .status(HttpStatus.OK)
                                        .build();
        log.info("Completed request for deleted user with:{}",categoryId);
        return new ResponseEntity<>(apiUserResponse,HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<PagebaleResponse>getAll(
            @RequestParam(value ="pageNumber",defaultValue = "0",required = false)Integer pageNumber,
            @RequestParam(value="pageSize",defaultValue = "10",required = false)Integer pageSize,
            @RequestParam(value="sortBy",defaultValue = "categoryId",required = false)String sortBy,
            @RequestParam(value="sortDir",defaultValue = "asc",required = false)String sortDir){
        log.info("Initiating request for getAll category");
        log.info("Completed request for getAll category");
       return new ResponseEntity<>(this.categoryService.getAll(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);

    }

    @GetMapping("/getSingle/{categoryId}")
    public ResponseEntity<CategoryDto>singleCat(@PathVariable Long categoryId){
        log.info("Initiating request for get single category with:{}",categoryId);
        CategoryDto single = this.categoryService.getSingle(categoryId);
        log.info("Completed request for get single category with:{}",categoryId);
        return new ResponseEntity<>(single,HttpStatus.OK);
    }

    @GetMapping("/searchCat/{keyword}")
    public ResponseEntity<List<CategoryDto>>searchCategory(@PathVariable String keyword){
        List<CategoryDto> categoryDtos = this.categoryService.serachCat(keyword);
        return new ResponseEntity<>(categoryDtos,HttpStatus.OK);
    }

    @PostMapping("/coverImage/{categoryId}")
    public ResponseEntity<ImageResponse>coverImage(
            @PathVariable Long categoryId ,@RequestParam("coverImage") MultipartFile coverImage1) throws IOException {
        log.info("Initiating request for coverImage category with:{}",categoryId);
        String image = this.coverService.coverImage(coverImage1, coverImage);
        CategoryDto single = this.categoryService.getSingle(categoryId);
        single.setCoverImage(image);
        CategoryDto update = this.categoryService.update(single, categoryId);
        ImageResponse imageResponse = ImageResponse.builder().imageName(image).message("Image successfully upload").success(true).status(HttpStatus.OK).build();
        log.info("Completed request for coverImage category with:{}",categoryId);
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }

    @GetMapping("/image/{userId}")
    public void serverUserImage(@PathVariable Long categoryId, HttpServletResponse response) throws IOException {
        CategoryDto category= this.categoryService.getSingle(categoryId);
        log.info("category CoverImage name :{}",category.getCoverImage());
        InputStream resource1 = this.coverService.getResource(coverImage, category.getCoverImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource1,response.getOutputStream());

    }

}
