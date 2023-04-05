package com.bikkadIt.electronic.store.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="products")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String title;
    @Column(length = 10000)
    private String description;
    private Double price;
    private Double discountPrice;
    private Integer quantity;
    private Date addedDate;
    private boolean live;
    private boolean stock;

}
