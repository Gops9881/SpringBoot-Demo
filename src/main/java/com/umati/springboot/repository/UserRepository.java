package com.umati.springboot.repository;

import com.umati.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
