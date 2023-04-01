package com.bikkadIt.electronic.store.services.impl;

import com.bikkadIt.electronic.store.dtos.PagebaleResponse;
import com.bikkadIt.electronic.store.dtos.UserDto;
import com.bikkadIt.electronic.store.entities.User;
import com.bikkadIt.electronic.store.exception.EmailNotFoundException;
import com.bikkadIt.electronic.store.exception.ResourceNotFoundException;
import com.bikkadIt.electronic.store.helper.AppConstance;
import com.bikkadIt.electronic.store.helper.Helper;
import com.bikkadIt.electronic.store.respository.UserRespository;
import com.bikkadIt.electronic.store.services.UserServices;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserImpl implements UserServices {

    Logger logger = LoggerFactory.getLogger(UserImpl.class);
    @Autowired
    private UserRespository userRespository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${user.profile.image.path}")
    private String imagePath;
    /**
     * @apiNote This method createUser
     * @param userDto
     * @return
     */
    @Override
    public UserDto createUser(UserDto userDto) {

       /* long l = Long.parseLong(UUID.randomUUID().toString());
        userDto.setUserId(Long.parseLong(String.valueOf(l)));*/
        logger.info("Initiating dao call for the save the user Details");
        User user1 = this.modelMapper.map(userDto, User.class);
        user1.setIsactive(AppConstance.YES);
        User saveUser = this.userRespository.save(user1);
        logger.info("Completed dao call for the save the user Details");
        return this.modelMapper.map(saveUser,UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userdto, long userId) {
        logger.info("Initiating dao call for update the user details with:{}", userId);
        User user = this.userRespository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstance.USER, AppConstance.USER_ID, userId));
        user.setName(userdto.getName());
        user.setPassword(userdto.getPassword());
        user.setAbout(userdto.getAbout());
        user.setImage(userdto.getImage());
        user.setGender(userdto.getGender());
        User updateUser = this.userRespository.save(user);
        logger.info("Complete dao call for update the user details: {}", userId);
        return this.modelMapper.map(updateUser,UserDto.class);
    }

    @Override
    public UserDto getByUserId(long userId) {
        logger.info("Initiating dao call for getUserId the user details with:{}",userId);
        User user = this.userRespository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstance.USER, AppConstance.USER_ID, userId));
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        logger.info("Complete dao call for getUserId the user details with:{}",userId);
        return userDto;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        logger.info("Initiating dao call for getEmail  details with:{}",email);
        User user = this.userRespository.findByEmail(email).orElseThrow(() -> new EmailNotFoundException("Email Not Found"));
        logger.info("Completed dao call for getEmail  details with:{}",email);
        return this.modelMapper.map(user,UserDto.class);
    }

    @Override
    public PagebaleResponse<UserDto> getByAllUser(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        logger.info("Initiating dao call for getAllUser  details ");
        Sort sort = (sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending());
        PageRequest page = PageRequest.of(pageNumber, pageSize,sort);
        Page<User> userList = this.userRespository.findAll(page);
        PagebaleResponse<UserDto> response = Helper.getPageableResponse(userList,
                UserDto.class);
        logger.info("Completed dao call for getAllUser  details ");
        return response;
    }

    @Override
    public void deleteUser(long userId) {
        logger.info("Initiating dao call for deleted user with:{}",userId);
        User user = this.userRespository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstance.USER, AppConstance.USER_ID, userId));
       //delete user profile image
        //image/user/abc.png

        String fullpath = imagePath + user.getImage();
       try {
           Path path = Paths.get(fullpath);
           Files.delete(path);
       }catch (NoSuchFileException ex){
        logger.info("User image not found is folder");
        ex.printStackTrace();
       }catch (IOException e){
            e.printStackTrace();
       }
        user.setIsactive(AppConstance.NO);
        logger.info("Completed dao call for deleted user with:{}",userId);
        this.userRespository.delete(user);
    }

    @Override
    public List<UserDto> UserSerach(String keyword) {
        logger.info("Initiating dao call for search user with:{}",keyword);
        List<User> serachUser = this.userRespository.findByNameContaining(keyword);
        List<UserDto> collect = serachUser.stream().map((user) -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        logger.info("Completed dao call for search user with:{}",keyword);
        return collect;

    }

   /* private UserDto userToDto(User saveUser) {

        UserDto userDto = UserDto.builder()
                .userId(saveUser.getUserId())
                .name(saveUser.getName())
                .email(saveUser.getEmail())
                .password(saveUser.getPassword())
                .about(saveUser.getAbout())
                .gender(saveUser.getGender())
                .image(saveUser.getImage())
                .build();

            return  userDto;

        }

        private User dtoToUser(UserDto userDto) {

            User user = User.builder()
                    .userId(userDto.getUserId())
                    .name(userDto.getName())
                    .email(userDto.getEmail())
                    .password(userDto.getPassword())
                    .about(userDto.getAbout())
                    .gender(userDto.getGender())
                    .image(userDto.getImage())
                    .build();


            return user;
        }
*/
        }



