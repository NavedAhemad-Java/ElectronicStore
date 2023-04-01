package com.bikkadIt.electronic.store.dtos;


import com.bikkadIt.electronic.store.validate.ImageNameValid;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CategoryDto extends BaseDtoClass {
    private Long categoryId;

    @NotBlank
    @Min(value=4,message = "title must be of minimum 4 characters !!")
    private String title;

    @NotBlank(message="Description required!!")
    private String description;

    @ImageNameValid
    private String coverImage;
    //other attributes if you have......

}
