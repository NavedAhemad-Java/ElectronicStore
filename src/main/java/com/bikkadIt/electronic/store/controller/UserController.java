package com.bikkadIt.electronic.store.controller;

import com.bikkadIt.electronic.store.dtos.PagebaleResponse;
import com.bikkadIt.electronic.store.dtos.UserDto;
import com.bikkadIt.electronic.store.helper.ApiUserResponse;
import com.bikkadIt.electronic.store.helper.AppConstance;
import com.bikkadIt.electronic.store.helper.ImageResponse;
import com.bikkadIt.electronic.store.services.FileService;
import com.bikkadIt.electronic.store.services.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserServices userServices;

    @Autowired
    private FileService fileService;


    @Value("${user.profile.image.path}")
    private String imageUplodPath;

    /**
     *
     * @param userDto
     * @return
     */
    //createUser

    @PostMapping
    public ResponseEntity<UserDto>createUser(@Valid @RequestBody UserDto userDto){
        logger.info("Initiating request for save user");
        UserDto user = this.userServices.createUser(userDto);
        logger.info("Completed request for save user");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /**
     *
     * @param userDto
     * @param userId
     * @return
     */
    //updateUser
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto>updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Long userId){
        logger.info("Initiating request for update user with:{}",userId);
        UserDto updateUser = this.userServices.updateUser(userDto, userId);
        logger.info("Completed request for update user with:{}",userId);
        return new ResponseEntity<>(updateUser,HttpStatus.OK);
    }

    /**
     *
     * @param userId
     * @return
     */

    //deleteUser
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiUserResponse>deleteUser(@PathVariable Long userId){
        logger.info("Initiating request for deleted user with:{}",userId);
        this.userServices.deleteUser(userId);
        ApiUserResponse message = ApiUserResponse.builder()
                                  .message(AppConstance.USER_DELETE)
                                  .success(true)
                                  .status(HttpStatus.OK)
                                  .build();
        logger.info("Completed request for deleted user with:{}",userId);
        return  new ResponseEntity<>(message,HttpStatus.OK);

    }

    /**
     *
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    //getAllUser
    @GetMapping("/getAll")
    public ResponseEntity<PagebaleResponse<UserDto>>getAllUser(@RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber
                                                    , @RequestParam(value="pageSize",defaultValue = "10",required = false)Integer pageSize,
                                                            @RequestParam(value="sortBy",defaultValue = "name",required = false)String sortBy,
                                                            @RequestParam(value="sorDir",defaultValue = "desc",required = false)String sortDir){
        logger.info("Initiating request for getAll user ");
        logger.info("Completed request for getAll user ");
        return new ResponseEntity<>(this.userServices.getByAllUser(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);

    }

    /**
     *
     * @param userId
     * @return
     */

    //SingleUser
    @GetMapping("/getId/{userId}")
    public ResponseEntity<UserDto>getUser(@PathVariable Long userId){
        logger.info("Initiating request for get single user with:{}",userId);
        UserDto userId1 = this.userServices.getByUserId(userId);
        logger.info("Completed request for get single user with:{}",userId);
        return new ResponseEntity<>(userId1,HttpStatus.OK);
    }

    /**
     *
     * @param email
     * @return
     */

    //getByEmail

    @GetMapping("/getEmail/{email}")
    public ResponseEntity<UserDto>getEmail(@PathVariable String email){
        logger.info("Initiating request for get email user with:{}",email);
        UserDto getEmail = this.userServices.getUserByEmail(email);
        logger.info("Completed request for get email user with:{}",email);
        return new ResponseEntity<>(getEmail,HttpStatus.OK);
    }

    /**
     *
     * @param keyword
     * @return
     */

    //searachUser
    @GetMapping("/serach/{keyword}")
    public ResponseEntity<List<UserDto>>serachUser(@PathVariable String keyword){
        logger.info("Initiating request for get search user with:{}",keyword);
        List<UserDto> userDtos = this.userServices.UserSerach(keyword);
        logger.info("Completed request for get search user with:{}",keyword);
        return new ResponseEntity<>(userDtos,HttpStatus.OK);

    }

    /**
     *
     * @param userId
     * @param image
     * @return
     * @throws IOException
     */

    //upload user image
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse>uplodUserImage(
            @PathVariable Long userId ,@RequestParam("userImage")MultipartFile image) throws IOException {

        String imageName = this.fileService.uplodImage(image, imageUplodPath);
        UserDto user = this.userServices.getByUserId(userId);
        user.setImage(imageName);
        UserDto userDto = this.userServices.updateUser(user, userId);
        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).message("Image successfully uplod").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }

    /**
     *
     * @param userId
     * @param response
     * @throws IOException
     */

    @GetMapping("/image/{userId}")
    public void serverUserImage(@PathVariable Long userId, HttpServletResponse response) throws IOException {
        UserDto user = this.userServices.getByUserId(userId);
        logger.info("User image name :{}",user.getImage());
        InputStream resource = this.fileService.getResource(imageUplodPath, user.getImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }
}
