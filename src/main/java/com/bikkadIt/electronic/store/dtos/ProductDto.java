package com.bikkadIt.electronic.store.dtos;

import com.bikkadIt.electronic.store.entities.Category;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto  extends BaseDtoClass{

    private Long productId;
    private String title;
    private String description;
    private Double price;
    private Double discountPrice;
    private Integer quantity;
    private Date addedDate;
    private boolean live;
    private boolean stock;
    private String productImage;
    private CategoryDto category;
}
