package com.umati.springboot.Controller;

import com.umati.springboot.customexceptions.ResourceNotFoundException;
import com.umati.springboot.model.User;
import com.umati.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
//@Api(value = "User Managment System",description = "It is used to manage the user data")
public class UserController {
    @Autowired
    UserService userService;
    //@ApiOperation(value = "List all user from the System", response = List.class)
    @RequestMapping(value = "/users" ,method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> list= userService.findAllUser();
        return new ResponseEntity<List<User>>(list,HttpStatus.OK);
    }
    //@ApiOperation(value = "Get the user details from the system with specifid user id",response = User.class) @ApiParam(value = "User id of the user to fetch",required = true)
    @RequestMapping(value = "/user/{id}" ,method = RequestMethod.GET)
    public ResponseEntity<User> getUser( @PathVariable("id") long id) throws ResourceNotFoundException {
        User list= userService.findById(id);
        return new ResponseEntity<User>(list,HttpStatus.OK);
    }
    //@ApiOperation(value="Create the user to the System",response = User.class) @ApiParam(value = "User Information to be added",required = true)
    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public User createUser( @Valid @RequestBody User user){
        return userService.addUser(user);
    }
    //@ApiOperation(value = "User information to be Updated",response = User.class) @ApiParam(value = "User id to be updated",required = true)
    @RequestMapping(value = "/user/{id}",method = RequestMethod.PUT )
    public ResponseEntity<User> updateUser( @PathVariable(value = "id") long id,@Valid @RequestBody User user) throws ResourceNotFoundException{
        User userUpdated=userService.updateUser(id,user);
        return ResponseEntity.ok(userUpdated);
    }
    //@ApiOperation(value = "User delete from the System") @ApiParam(value = "User id to be deleted from the system",required = true )
    @RequestMapping(value="/user/{id}",method = RequestMethod.DELETE)
    public Map<String, Boolean> deleteUser( @PathVariable(value = "id") long id) throws ResourceNotFoundException{
            return userService.deleteUser(id);
    }
}
