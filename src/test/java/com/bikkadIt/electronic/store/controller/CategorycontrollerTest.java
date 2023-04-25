package com.bikkadIt.electronic.store.controller;

import com.bikkadIt.electronic.store.BaseTestController;
import com.bikkadIt.electronic.store.dtos.CategoryDto;
import com.bikkadIt.electronic.store.dtos.PagebaleResponse;
import com.bikkadIt.electronic.store.dtos.ProductDto;
import com.bikkadIt.electronic.store.entities.Category;
import com.bikkadIt.electronic.store.entities.Product;
import com.bikkadIt.electronic.store.respository.CategoryRepository;
import com.bikkadIt.electronic.store.services.CategoryService;
import com.bikkadIt.electronic.store.services.ProductServiceI;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategorycontrollerTest extends BaseTestController {
    @MockBean
    private CategoryRepository categoryRepository;
    @MockBean
    private CategoryService categoryService;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductServiceI productServiceI;

    Category category = Category.builder()
            .title("Electronic category")
            .description("selection for electronic itam")
            .coverImage("adf.png")
            .build();
    Product product = Product.builder().title("LCD TV").description("realme 36 cm and sound bast quality").price(23645.25)
            .quantity(1)
            .discountPrice(5.00)
            .stock(true)
            .live(true).build();
    public void init1() {
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
    @BeforeEach
    public void init(){
        category = Category.builder()
                .title("Electronic category")
                .description("selection for electronic itam")
                .coverImage("adf.png")
                .build();

    }
    @Test
    public void createCategoryTest() throws Exception {

        CategoryDto dto = mapper.map(category, CategoryDto.class);

        Mockito.when(categoryService.create(Mockito.any())).thenReturn(dto);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/cat/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(category))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());
    }
    @Test
    public void updateCategoryTest() throws Exception {

        Long categoryId=1L;

        CategoryDto dto = this.mapper.map(category, CategoryDto.class);

        Mockito.when(categoryService.update(Mockito.any(),Mockito.anyLong())).thenReturn(dto);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/cat/update/"+categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(category))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());

    }

    private String convertObjectToJsonString(Category category) {
        try {
            return new ObjectMapper().writeValueAsString(category);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    @Test
    public void getAllCategoryTest() throws Exception {

        CategoryDto cat = CategoryDto.builder().title("Eelectronic Store").description("sale for Electronic Itam").coverImage("abc.png").build();
        CategoryDto cat1 = CategoryDto.builder().title("Eelectronic Store1").description("sale for Electronic Itam").coverImage("abc.png").build();
        CategoryDto cat2 = CategoryDto.builder().title("Eelectronic Store2").description("sale for Electronic Itam").coverImage("abc.png").build();
        CategoryDto cat3 = CategoryDto.builder().title("Eelectronic Store3").description("sale for Electronic Itam").coverImage("abc.png").build();
        PagebaleResponse<CategoryDto> pegeableResponse=new PagebaleResponse<>();
        pegeableResponse.setContent(Arrays.asList(cat,cat1,cat2,cat3));
        pegeableResponse.setLastpage(false);
        pegeableResponse.setPageSize(10);
        pegeableResponse.setPageNumber(100);
        pegeableResponse.setTotalElements(1000);
        Mockito.when(categoryService.getAll(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pegeableResponse);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/cat/getAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());


    }

    @Test
    void getId() throws Exception {
        Long categoryId=1L;
        Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(category));
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/cat/delete/"+categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void singleCat() throws Exception {
        Long categoryId=1L;
        CategoryDto categoryDto = CategoryDto.builder()
                .title("dafdsdfds")
                .description("dadfadef")
                .coverImage("abc.png")
                .build();
        Mockito.when(categoryService.getSingle(Mockito.anyLong())).thenReturn(categoryDto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/cat/getSingle/"+categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void searchCategory() throws Exception {
        Category cat = Category.builder()
                .title("Electronic category")
                .description("selection for electronic itam")
                .coverImage("adf.png")
                .build();
        Category cat1= Category.builder()
                .title("Electronic category")
                .description("selection for electronic itam")
                .coverImage("adf.png")
                .build();
        Category cat2 = Category.builder()
                .title("Electronic category")
                .description("selection for electronic itam")
                .coverImage("adf.png")
                .build();
        String keywords="Ahemad";
        Mockito.when(categoryRepository.findByTitleContaining(Mockito.anyString())).thenReturn( Arrays.asList(cat,cat1,cat2));
        List<CategoryDto> categoryDtos = categoryService.serachCat(keywords);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/cat/searchCat/"+keywords)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void coverImage() {
    }

    @Test
    void serverUserImage() {
    }

    @Test
    void createProductWithCategory() throws Exception {
        Long categoryId=1L;
        CategoryDto dto = mapper.map(category, CategoryDto.class);
        ProductDto productDto = mapper.map(product, ProductDto.class);
        Mockito.when(productServiceI.createWithCategory(Mockito.any(),Mockito.anyLong())).thenReturn(productDto);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/cat/{categoryId}/products",categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(category))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());
    }


    @Test
    void updateCategoryWithProduct() throws Exception {
        Long categoryId=1L;
        Long productId=1L;
        CategoryDto dto = this.mapper.map(category, CategoryDto.class);
        ProductDto productDto = mapper.map(product, ProductDto.class);

        Mockito.when(productServiceI.updateCateogry(Mockito.anyLong(),Mockito.anyLong())).thenReturn(productDto);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/cat/{categoryId}/products/{productId}",categoryId,productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(category))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());

    }

    @Test
    void testUpdateCategoryWithProduct() throws Exception {
        Long categoryId=1L;
        ProductDto pro = ProductDto.builder().title("LCD TV")
                .description("realme 36 cm and sound bast quality")
                .price(23645.25)
                .quantity(1)
                .discountPrice(5.00)
                .stock(true)
                .live(true)
                .build();

        ProductDto pro1 = ProductDto.builder().title("LCD TV")
                .description("realme 36 cm and sound bast quality")
                .price(23645.25)
                .quantity(1)
                .discountPrice(5.00)
                .stock(true)
                .live(true)
                .build();

        ProductDto pro2 = ProductDto.builder().title("LCD TV")
                .description("realme 36 cm and sound bast quality")
                .price(23645.25)
                .quantity(1)
                .discountPrice(5.00)
                .stock(true)
                .live(true)
                .build();

        ProductDto pro3 = ProductDto.builder().title("LCD TV")
                .description("realme 36 cm and sound bast quality")
                .price(23645.25)
                .quantity(1)
                .discountPrice(5.00)
                .stock(true)
                .live(true)
                .build();
        PagebaleResponse<ProductDto> pegeableResponse=new PagebaleResponse<>();
        pegeableResponse.setContent(Arrays.asList(pro,pro1,pro2,pro3));
        pegeableResponse.setLastpage(false);
        pegeableResponse.setPageSize(10);
        pegeableResponse.setPageNumber(100);
        pegeableResponse.setTotalElements(1000);
        Mockito.when(productServiceI.getAllOfCategory(Mockito.anyLong(),Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pegeableResponse);

        this.mockMvc.perform(get("/cat/{categoryId}/products",categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}