package com.bikkadIt.electronic.store.dtos;

import com.bikkadIt.electronic.store.validate.ImageNameValid;
import lombok.*;

import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto extends BaseDtoClass{


    private Long userId;

    @Size(min=3,max=15,message = "Username must be min 4 Character")
    private String name;

 //   @Email(message = "Invalid User Email")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$",message = "Invalid User Email !!")
    @NotBlank(message = "Email is required!!")
    private String email;


    @NotBlank(message = "Password is required !!")
    private String password;
    @Size(min=4,max=6,message = "Invalid gender!!")
    private String gender;
    @NotBlank(message = "Write something about yourself")
    private String about;

    @ImageNameValid
    private String image;


}
