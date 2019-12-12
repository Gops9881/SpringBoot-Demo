package com.umati.springboot.service;

import com.umati.springboot.customexceptions.ResourceNotFoundException;
import com.umati.springboot.model.User;
import com.umati.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("userService")
public class UserServiceImpl implements UserService ,UserDetailsService{

    @Autowired
    UserRepository userrepo;
    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Override
    public User getUserById(long id) throws ResourceNotFoundException {

        User list = userrepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not avaiable"));
        return list;
    }

    @Override
    public List<User> findAllUser() {
        List<User> list = new ArrayList<>();
        userrepo.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public User addUser(User user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setfullname(user.getfullname());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setMobile(user.getMobile());
        newUser.setAddress(user.getAddress());
        newUser.setActivated(false);
        return userrepo.save(newUser);

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
        usertoUpdate.setfullname(user.getfullname());
        usertoUpdate.setAddress(user.getAddress());
        usertoUpdate.setUsername(user.getUsername());
        usertoUpdate.setMobile(user.getMobile());
        usertoUpdate.setActivated(user.isActivated());
        final User userUpdated=userrepo.save(usertoUpdate);
        return userUpdated;
    }

    @Override
    public User getUserByUsername(String username) throws ResourceNotFoundException {
        User user=userrepo.findByUsername(username);
        if(user == null)throw new UsernameNotFoundException(username);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userrepo.findByUsername(username);
        if(user == null) throw new UsernameNotFoundException(username);
        if(user.isActivated()){
            return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),new ArrayList<>());
        }else{
           throw new UsernameNotFoundException(username);
        }
    }
}
