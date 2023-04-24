package com.bikkadIt.electronic.store.respository;

import com.bikkadIt.electronic.store.entities.Category;
import com.bikkadIt.electronic.store.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    //search
    Page<Product>findByTitleContaining(String subTitle,Pageable pageable);
    Page<Product> findByLiveTrue(Pageable pageable);

    Page<Product>findByCategory(Category category,Pageable pageable);
    //other methods
    //custom finder methods
    //uery methods


}
