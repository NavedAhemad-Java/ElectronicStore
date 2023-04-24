package com.bikkadIt.electronic.store.controller;


import com.bikkadIt.electronic.store.BaseTestController;
import com.bikkadIt.electronic.store.dtos.PagebaleResponse;
import com.bikkadIt.electronic.store.dtos.UserDto;
import com.bikkadIt.electronic.store.entities.User;
import com.bikkadIt.electronic.store.respository.UserRespository;
import com.bikkadIt.electronic.store.services.UserServices;
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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



public class UserControllerTest extends BaseTestController {
    @MockBean
    private UserRespository userRespository;
    @MockBean
    private UserServices userServices;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    User user;
    @BeforeEach
    public void init(){
        user = User.builder()
                .name("Naved")
                .email("navedahemad20@gmail.com")
                .password("12345")
                .gender("Male")
                .about("dcdfytq")
                .image("abc.png")
                .build();

    }
    @Test
    public void createUserTest() throws Exception {

        UserDto dto = mapper.map(user, UserDto.class);

        Mockito.when(userServices.createUser(Mockito.any())).thenReturn(dto);


        this.mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());
    }
@Test
    public void updateUserTest() throws Exception {
        //users/{userId}+Put request+json
        Long userId=1L;
        UserDto dto = this.mapper.map(user, UserDto.class);
        Mockito.when(userServices.updateUser(Mockito.any(),Mockito.anyLong())).thenReturn(dto);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/user/"+userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());

    }

    private String convertObjectToJsonString(Object user) {

        try {
            return new ObjectMapper().writeValueAsString(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
@Test
    public void getAllUserTest() throws Exception {

        UserDto obj1 = UserDto.builder().name("naved").email("navedahemad@gmail.com").password("abc").image("abc.png").about("testing").build();
        UserDto obj2 = UserDto.builder().name("shubham").email("navedahemad@gmail.com").password("abc").image("abc.png").about("testing").build();
        UserDto obj3 = UserDto.builder().name("faizan").email("navedahemad@gmail.com").password("abc").image("abc.png").about("testing").build();
        UserDto obj4 = UserDto.builder().name("zeeshan").email("navedahemad@gmail.com").password("abc").image("abc.png").about("testing").build();
        PagebaleResponse<UserDto>pegeableResponse=new PagebaleResponse<>();
        pegeableResponse.setContent(Arrays.asList(obj1,obj2,obj3,obj4));
        pegeableResponse.setLastpage(false);
        pegeableResponse.setPageSize(10);
        pegeableResponse.setPageNumber(100);
        pegeableResponse.setTotalElements(1000);
        Mockito.when(userServices.getByAllUser(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pegeableResponse);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/getAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void getByUserIdTest() throws Exception {
        Long userId=1L;
        UserDto userDto=UserDto.builder()
                .name("Naveed Ahemad")
                .email("naved142@gmail.com")
                .about("this is updated user about details")
                .gender("Male")
                .image("xyz.png")
                .build();
        Mockito.when(userServices.getByUserId(Mockito.anyLong())).thenReturn(userDto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/getId/"+userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void deleteUserTest() throws Exception {
        Long userId=1L;
       Mockito.when(userRespository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/user/"+userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void getEmail() throws Exception {
        String emailId="navedahemad20@gmail.com";
        UserDto userDto=UserDto.builder()
                .name("Naveed Ahemad")
                .email("naved142@gmail.com")
                .about("this is updated user about details")
                .gender("Male")
                .image("xyz.png")
                .build();
        Mockito.when(userServices.getByUserId(Mockito.anyLong())).thenReturn(userDto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/getEmail/"+emailId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());


    }

    @Test
    void serachUser() throws Exception {
        User user1 = User.builder()
                .name("Naveed Ahemad")
                .email("navedahemad20@gmail.com")
                .about("This is testing create method")
                .gender("Male")
                .image("abc.png")
                .password("abc").build();

        User user2 = User.builder()
                .name("Ahemad")
                .email("navedahemad20@gmail.com")
                .about("This is testing create method")
                .gender("Male")
                .image("abc.png")
                .password("abc").build();


        User user3 = User.builder()
                .name("Ahemad")
                .email("navedahemad20@gmail.com")
                .about("This is testing create method")
                .gender("Male")
                .image("abc.png")
                .password("abc").build();
        String keywords="Ahemad";
    Mockito.when(userRespository.findByNameContaining(Mockito.anyString())).thenReturn( Arrays.asList(user1,user2,user3));
        List<UserDto> userDtos = userServices.UserSerach(keywords);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/serach/"+keywords)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void uplodUserImage() {





    }

    @Test
    void serverUserImage() {
    }
}
