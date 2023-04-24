package com.bikkadIt.electronic.store.service;


import com.bikkadIt.electronic.store.BaseTest;
import com.bikkadIt.electronic.store.dtos.PagebaleResponse;
import com.bikkadIt.electronic.store.dtos.UserDto;
import com.bikkadIt.electronic.store.entities.User;
import com.bikkadIt.electronic.store.respository.UserRespository;
import com.bikkadIt.electronic.store.services.UserServices;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class UserServiceTest extends BaseTest {
    @MockBean
    private UserRespository userRespository;

    @Autowired
    private UserServices userServices;
    User user;

    @Autowired
    private ModelMapper mapper;

    @BeforeEach
    public void init(){
        user = User.builder()
                .name("Naveed")
                .email("navedahemad20@gmail.com")
                .about("This is testing create method")
                .gender("Male")
                .image("abc.png")
                .password("abc").build();
    }


    //create user Test

    @Test
    public void createUserTest(){
        Mockito.when(userRespository.save(Mockito.any())).thenReturn(user);
        UserDto create = userServices.createUser(mapper.map(user, UserDto.class));
        System.out.println(create.getName());
        Assertions.assertNotNull(create);
        Assertions.assertEquals("Naveed",create.getName());
    }

    //update User Test
    @Test
    public void updateUserTest(){
        Long userId= 1L;
        UserDto userDto= UserDto.builder()
                .name("Naveed Ahemad")
                .email("naved142@gmail.com")
                .about("this is updated user about details")
                .gender("Male")
                .image("xyz.png")
                .build();

        Mockito.when(userRespository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        Mockito.when(userRespository.save(Mockito.any())).thenReturn(user);
        UserDto updateUser = userServices.updateUser(userDto, userId);
       // UserDto updateUser = mapper.map(user, UserDto.class);
        System.out.println(updateUser.getName());
        System.out.println(updateUser.getImage());
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(userDto.getName(),updateUser.getName(),"Name Not found");

    }
    @Test
    public void deleteUserTest(){
        Long userId=1L;
        Mockito.when(userRespository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        userServices.deleteUser(userId);
        Mockito.verify(userRespository,Mockito.timeout(1)).delete(user);
    }
    //getAll User Test
    @Test
    public void getAllUserTest(){
       User user1 = User.builder()
                .name("Shubham")
                .email("navedahemad20@gmail.com")
                .about("This is testing create method")
                .gender("Male")
                .image("abc.png")
                .password("abc").build();
    
    User user2 = User.builder()
            .name("Zeeshan")
                .email("navedahemad20@gmail.com")
                .about("This is testing create method")
                .gender("Male")
                .image("abc.png")
                .password("abc").build();

        List<User>userList= Arrays.asList(user,user1,user2);
        Page<User> page= new PageImpl<>(userList);
        Mockito.when(userRespository.findAll((Pageable) Mockito.any())).thenReturn(page);
        PagebaleResponse<UserDto> allUser = userServices.getByAllUser(1,2,"name","asc");
        Assertions.assertEquals(3,allUser.getContent().size());
    }

    //getSingle User Test

    @Test
    public void getByUserIdTest(){

        Long userId=1L;
        UserDto userDto= UserDto.builder()
                .name("Naveed Ahemad")
                .email("naved142@gmail.com")
                .about("this is updated user about details")
                .gender("Male")
                .image("xyz.png")
                .build();

        Mockito.when(userRespository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        UserDto byUserId = userServices.getByUserId(userId);
        System.out.println(byUserId.getUserId());
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(userDto.getUserId(),byUserId.getUserId());
    }

    @Test
    public void getByEmailIdTest(){

        String emailId="";
        UserDto userDto= UserDto.builder()
                .name("Naveed Ahemad")
                .email("navedahemad20@gmail.com")
                .about("this is updated user about details")
                .gender("Male")
                .image("xyz.png")
                .build();
        Mockito.when(userRespository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        UserDto userByEmail = userServices.getUserByEmail(emailId);
        System.out.println(userByEmail.getEmail());
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(userDto.getEmail(),userByEmail.getEmail());
    }

    @Test
    public void searchUserTest(){

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
        Mockito.when(userRespository.findByNameContaining(keywords)).thenReturn(Arrays.asList(user,user1,user2,user3));
        List<UserDto> userDto = userServices.UserSerach(keywords);
        Assertions.assertEquals(4,userDto.size(),"size not matched!!");
    }


}
