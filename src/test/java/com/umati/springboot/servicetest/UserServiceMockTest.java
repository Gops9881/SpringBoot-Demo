package com.umati.springboot.servicetest;

import com.umati.springboot.customexceptions.ResourceNotFoundException;
import com.umati.springboot.model.User;
import com.umati.springboot.repository.UserRepository;
import com.umati.springboot.service.UserService;
import com.umati.springboot.service.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceMockTest {
    List<User> useerList = new ArrayList<>();
    User user =new User();
    Map<String,Boolean> resp = new HashMap<String, Boolean>();
    @Mock
    UserRepository repo;
    @Mock
    UserServiceImpl userImp;
    @Mock
    UserService userService;

    @Before
    public void userList(){
        user.setActivated(true);
        user.setfullname("Abc Abc");
        user.setAddress("Magdeburg");
        user.setMobile(999999);
        user.setUsername("Abc@Abc");
        user.setId(1);
        useerList.add(user);
        resp.put("User Deleted", true);
    }
    @Test
    public void testFindAllUsers(){
        Mockito.when(userService.findAllUser()).thenReturn(useerList);
        List<User> result = userService.findAllUser();
        Assert.assertEquals(useerList,result);
    }
    @Test
    public void testGetUserById() throws ResourceNotFoundException {
        Mockito.when(userService.getUserById(1L)).thenReturn(user);
        User u= userService.getUserById(1L);
        Assert.assertEquals(u.getId(),1L);
    }
    @Test
    public void testGetUserByUserName() throws ResourceNotFoundException {
        Mockito.when(userService.getUserByUsername("Abc@Abc")).thenReturn(user);
        User u= userService.getUserByUsername("Abc@Abc");
        Assert.assertEquals(u.getUsername(),"Abc@Abc");
    }
    @Test
    public void testAddUser() throws ResourceNotFoundException {
        Mockito.when(userService.addUser(user)).thenReturn(user);
        User u= userService.addUser(user);
        Assert.assertEquals(u, user);
    }
    @Test
    public void testDeleteUser() throws ResourceNotFoundException {
        Mockito.when(userService.deleteUser(1L)).thenReturn(resp);
        Map<String,Boolean> result= userService.deleteUser(1L);
        Assert.assertEquals(result.get("User Deleted"),true);
    }
    @Test
    public void testUpdateUser() throws ResourceNotFoundException {
        Mockito.when(userService.updateUser(1L,user)).thenReturn(user);
        User result= userService.updateUser(1l,user);
        Assert.assertEquals(result,user);
    }
}
