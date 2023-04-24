package com.bikkadIt.electronic.store.controller;

import com.bikkadIt.electronic.store.BaseTestController;
import com.bikkadIt.electronic.store.dtos.PagebaleResponse;
import com.bikkadIt.electronic.store.dtos.ProductDto;
import com.bikkadIt.electronic.store.entities.Product;
import com.bikkadIt.electronic.store.respository.ProductRepository;
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
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductControllerTest extends BaseTestController {
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private ProductServiceI productServiceI;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private MockMvc mockMvc;

    Product product;

    @BeforeEach
    public void init(){
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
    //create Product Test
    @Test
    public void createProductTest() throws Exception {
        ProductDto dto = mapper.map(product, ProductDto.class);

        Mockito.when(this.productServiceI.createProduct(Mockito.any())).thenReturn(dto);


        this.mockMvc.perform(MockMvcRequestBuilders.post("/products/createProduct")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(product))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());
    }
    //Update Product Test

    @Test
    public void updateProductTest() throws Exception {
        Long productId=1L;

        ProductDto dto = this.mapper.map(product, ProductDto.class);

        Mockito.when(productServiceI.updateProduct(Mockito.any(),Mockito.anyLong())).thenReturn(dto);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/products/updateProduct/"+productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());

    }

    private String  convertObjectToJsonString(Object product) {
        try {
            return new ObjectMapper().writeValueAsString(product);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    //GetAll Product Test

    @Test
    public void getAllProductTest() throws Exception {
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
        Mockito.when(productServiceI.getAllProduct(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pegeableResponse);

        this.mockMvc.perform(get("/products/allProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());



    }

    //Get All Live Product
    @Test
    public void getAllLiveTest() throws Exception {
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
        Mockito.when(productServiceI.getAllLive(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pegeableResponse);

        this.mockMvc.perform((get("/products/liveProduct"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    //Search Product Test
    @Test
    public void getAllSearchTest() throws Exception {

        String query ="LCD TV";

        ProductDto pro = ProductDto.builder().title("LCD TV")
                .description("realme 36 cm and sound bast quality")
                .price(23645.25)
                .quantity(1)
                .discountPrice(5.00)
                .stock(true)
                .live(true)
                .build();

        ProductDto pro1 = ProductDto.builder().title("LED TV")
                .description("realme 36 cm and sound bast quality")
                .price(23645.25)
                .quantity(1)
                .discountPrice(5.00)
                .stock(true)
                .live(true)
                .build();

        ProductDto pro2 = ProductDto.builder().title("LVD TV")
                .description("realme 36 cm and sound bast quality")
                .price(23645.25)
                .quantity(1)
                .discountPrice(5.00)
                .stock(true)
                .live(true)
                .build();

        ProductDto pro3 = ProductDto.builder().title("LID TV")
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
        Mockito.when(productServiceI.searchByTitle(Mockito.anyString(),Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pegeableResponse);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/products/searchProduct/{query}",query)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }
    //Delete Product Test

    @Test
    public void deleteUserProductTest() throws Exception {
        Long proudctId=1L;


        Mockito.when(this.productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product));
        this.productServiceI.deleteProduct(proudctId);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/products/delete/"+proudctId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON) )
                .andDo(print())
                .andExpect(status().isOk());

    }

    //getsingle Product Test
    @Test
    public void getSinglePrdouctTest() throws Exception {

        Long productId=1L;

        ProductDto dto = ProductDto.builder()
                .title("LCD TV")
                .description("realme 36 cm and sound bast quality")
                .price(23645.25)
                .quantity(1)
                .discountPrice(5.00)
                .stock(true)
                .live(true)
                .build();
        Mockito.when(this.productServiceI.getSingleProduct(Mockito.anyLong())).thenReturn(dto);
        this.mockMvc.perform(get("/products/singleProduct/"+productId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }



}
