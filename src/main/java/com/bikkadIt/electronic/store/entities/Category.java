package com.bikkadIt.electronic.store.entities;

import lombok.*;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="categories")
public class Category  extends BaseEntity{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long categoryId;
        @Column(name="category_title",length = 60)
        private String title;
        @Column(name="category_desc",length = 50)
        private String description;
        private String coverImage;
        //other attributes if you have......

        @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
        private List<Product> products= new ArrayList<>();



}

