package com.bikkadIt.electronic.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
