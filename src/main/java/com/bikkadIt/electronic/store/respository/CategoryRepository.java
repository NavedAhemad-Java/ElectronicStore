package com.bikkadIt.electronic.store.respository;

import com.bikkadIt.electronic.store.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    List<Category> findByTitleContaining(String keyword);
}
