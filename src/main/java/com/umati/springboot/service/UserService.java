package com.umati.springboot.service;

import com.umati.springboot.customexceptions.ResourceNotFoundException;
import com.umati.springboot.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User getUserById(long id) throws ResourceNotFoundException;
    List<User> findAllUser();
    User addUser(User user);
    Map<String, Boolean> deleteUser(long id) throws ResourceNotFoundException;
    User updateUser(long id,User user) throws ResourceNotFoundException;
    User getUserByUsername(String username) throws ResourceNotFoundException;
}
