package com.bikkadIt.electronic.store.service;

import com.bikkadIt.electronic.store.BaseTest;
import com.bikkadIt.electronic.store.dtos.CategoryDto;
import com.bikkadIt.electronic.store.dtos.PagebaleResponse;
import com.bikkadIt.electronic.store.entities.Category;
import com.bikkadIt.electronic.store.respository.CategoryRepository;
import com.bikkadIt.electronic.store.services.CategoryService;
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

public class CategoryServiceTest extends BaseTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    Category category;
    @Autowired
    private ModelMapper mapper;

    @BeforeEach
    public void init() {
        category = Category.builder()
                .title("Electronic category")
                .description("selection for electronic itam")
                .coverImage("adf.png")
                .build();
    }



    //create category Test
    @Test
    public void createCategoryTest(){
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        CategoryDto categoryDto = categoryService.create(mapper.map(category, CategoryDto.class));
        System.out.println(categoryDto.getTitle());
        Assertions.assertNotNull(categoryDto);
        Assertions.assertEquals("Electronic category",categoryDto.getTitle());
    }

    //update category Test
    @Test
    public void updateCategoryTest(){
        Long categoryId=1L;
        CategoryDto dto = CategoryDto.builder()
                .title("Electronic Store")
                .description("Online mode purchase for electronic Item")
                .coverImage("xyz.png")
                .build();
        Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        CategoryDto update = categoryService.update(dto, categoryId);
        System.out.println(update.getTitle());
        System.out.println(update.getCoverImage());
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(dto.getTitle(),update.getTitle(),"Category not found");


    }
    @Test
    public void deleteCategoryTest(){
        Long categoryId=1L;
        Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(category));
        categoryService.delete(categoryId);
        Mockito.verify(categoryRepository,Mockito.timeout(1)).delete(category);
    }
    @Test
    public void getAllCategoryTest(){
        Category category1 = Category.builder()
                .title("electronic store")
                .description("this is purchesing for electronic itam")
                .coverImage("abc.png")
                .build();
        Category category2 = Category.builder()
                .title("electronic store")
                .description("this is purchesing for electronic itam")
                .coverImage("abc.png")
                .build();

        Category category3 = Category.builder()
                .title("electronic store")
                .description("this is purchesing for electronic itam")
                .coverImage("abc.png")
                .build();


        List<Category> categoryList= Arrays.asList(category,category1,category2,category3);
        Page<Category> page= new PageImpl<>(categoryList);
        Mockito.when(categoryRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
        PagebaleResponse<CategoryDto> all = categoryService.getAll(1, 2, "name", "asc");
        Assertions.assertEquals(4,all.getContent().size());
    }
    @Test
    public void getSingleCategoryTest(){
        Long categoryId=1L;
        CategoryDto dto = CategoryDto.builder()
                .title("dafdsdfds")
                .description("dadfadef")
                .coverImage("abc.png")
                .build();
        Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(category));
        CategoryDto single = categoryService.getSingle(categoryId);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(dto.getCategoryId(),single.getCategoryId());


    }
     @Test
    public void searchCategoryTest(){
        Category cat = Category.builder()
                .title("electronic store")
                .description("this is electronic itam")
                .coverImage("erer.png")
                .build();

        Category cat1 = Category.builder()
                .title("electronic store")
                .description("this is electronic itam")
                .coverImage("erer.png")
                .build();
        Category cat2 = Category.builder()
                .title("electronic store")
                .description("this is electronic itam")
                .coverImage("erer.png")
                .build();
        String keywords="Ahemad";


        Mockito.when(categoryRepository.findByTitleContaining(keywords)).thenReturn(Arrays.asList(category,cat,cat1,cat2));
        List<CategoryDto> categoryDtos = categoryService.serachCat(keywords);
        Assertions.assertEquals(4,categoryDtos.size(),"size not matched!!");
    }
    }



