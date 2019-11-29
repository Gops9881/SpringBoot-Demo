package com.umati.springboot.service;

import com.umati.springboot.customexceptions.ResourceNotFoundException;
import com.umati.springboot.model.User;
import com.umati.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userrepo;


    @Override
    public User findById(long id) throws ResourceNotFoundException {

        User list = userrepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not avaiable"));
        return list;
    }

    @Override
    public List<User> findAllUser() {
        List<User> list= userrepo.findAll();
        return list;
    }

    @Override
    public User addUser(User user) {
        return  userrepo.save(user);
    }

    @Override
    public Map<String, Boolean> deleteUser(long id) throws ResourceNotFoundException {
        User usertoDelete=userrepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User not available to delete"+id));
        userrepo.delete(usertoDelete);
        Map<String,Boolean> response=new HashMap<>();
        response.put("User deleted",Boolean.TRUE);
        return response;
    }

    @Override
    public User updateUser(long id,User user) throws ResourceNotFoundException {
        User usertoUpdate=userrepo.findById(id).orElseThrow(()->new ResourceNotFoundException("No user to update"+id));
        usertoUpdate.setFullName(user.getFullName());
        usertoUpdate.setAddress(user.getAddress());
        usertoUpdate.setUsername(user.getUsername());
        usertoUpdate.setMobile(user.getMobile());
        final User userUpdated=userrepo.save(usertoUpdate);
        return userUpdated;
    }
}
