package com.umati.springboot.usercontrollertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umati.springboot.controller.UserController;
import com.umati.springboot.model.User;
import com.umati.springboot.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootConfiguration
public class ControllerTest {
    List<User> useerList = new ArrayList<>();
    User user1 =new User();
    User user2 =new User();
    Map<String,Boolean> resp = new HashMap<String, Boolean>();


    private MockMvc mockMVC;

    @Mock
    UserService userService;

    @Spy
    @InjectMocks
    private UserController controller= new UserController();

    @Before
    public void userList(){
        this.mockMVC=MockMvcBuilders.standaloneSetup(controller).build();
        user1.setActivated(true);
        user1.setfullname("Abc Abc");
        user1.setAddress("Magdeburg");
        user1.setMobile(999999);
        user1.setUsername("Abc@Abc");
        user1.setId(1);
        useerList.add(user1);
        resp.put("User Deleted", true);
        user2.setActivated(true);
        user2.setfullname("Xyz Xyz");
        user2.setAddress("Magdeburg");
        user2.setMobile(999999);
        user2.setUsername("Xyz@Xyz");
        user2.setId(2);
        useerList.add(user2);
    }
    @Test
    public void testFindAllUsers() throws Exception {
        Mockito.when(userService.findAllUser()).thenReturn(useerList);
        this.mockMVC.perform(MockMvcRequestBuilders.get("/users")).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].username", is("Abc@Abc")))
                .andExpect(jsonPath("$[0].address", is("Magdeburg")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].username", is("Xyz@Xyz")))
                .andExpect(jsonPath("$[1].address", is("Magdeburg")));
    }
    @Test
    public void testGetUserById() throws Exception {
        Mockito.when(userService.getUserById(Mockito.anyLong())).thenReturn(user1);
        this.mockMVC.perform(MockMvcRequestBuilders.get("/user/id/1")).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.username", is("Abc@Abc")))
                .andExpect(jsonPath("$.address", is("Magdeburg")));
    }
    @Test
    public void testGetUserByUserName() throws Exception {
        Mockito.when(userService.getUserByUsername("Abc@Abc")).thenReturn(user1);
        this.mockMVC.perform(MockMvcRequestBuilders.get("/user/username/Abc@Abc")).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.username", is("Abc@Abc")))
                .andExpect(jsonPath("$.address", is("Magdeburg")));
    }
    @Test
    public void testAddUser() throws Exception {
        Mockito.when(userService.addUser(Mockito.any(User.class))).thenReturn(user1);
        ObjectMapper mapper=new ObjectMapper();
        String jsonString=mapper.writeValueAsString(user1);
        this.mockMVC.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON).content(jsonString).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }
    @Test
    public void testDeleteUser() throws Exception {
        Mockito.when(userService.deleteUser(Mockito.anyLong())).thenReturn(resp);
        this.mockMVC.perform(MockMvcRequestBuilders.delete("/user/1")).andExpect(status().isOk());
    }
    @Test
    public void testUpdateUser() throws Exception {
        ObjectMapper mapper=new ObjectMapper();
        String jsonString=mapper.writeValueAsString(user1);
        Mockito.when(userService.updateUser(1L,user1)).thenReturn(user1);
        this.mockMVC.perform(MockMvcRequestBuilders.put("/user/1").contentType(MediaType.APPLICATION_JSON).content(jsonString).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}
