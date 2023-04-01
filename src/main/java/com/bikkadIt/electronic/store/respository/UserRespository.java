package com.bikkadIt.electronic.store.respository;

import com.bikkadIt.electronic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRespository extends JpaRepository<User,Long>{


    Optional<User> findByEmail(String email);

   Optional<User> findByEmailAndPassword(String email, String password);

    List<User>findByNameContaining(String keyword);
}
