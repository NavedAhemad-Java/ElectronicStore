package com.bikkadIt.electronic.store.respository;

import com.bikkadIt.electronic.store.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRespository extends JpaRepository<Category,Long> {
}
