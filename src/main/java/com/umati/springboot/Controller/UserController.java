package com.umati.springboot.Controller;

import com.google.common.net.MediaType;
import com.umati.springboot.customexceptions.ResourceNotFoundException;
import com.umati.springboot.model.JwtRequest;
import com.umati.springboot.model.User;
import com.umati.springboot.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.apache.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "User Managment System",description = "It is used to manage the user data")
public class UserController {
    public static final  Logger logger= Logger.getLogger(UserController.class);
    @Autowired
    UserService userService;
    final static Logger log=Logger.getLogger(UserController.class);
    @ApiOperation(value = "List all user from the System", response = List.class)
    @RequestMapping(value = "/users" ,method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers() {
        logger.info("listAllUsers method called to retrive all suers");
        List<User> list= userService.findAllUser();
        logger.info("listAllUsers method sending all suers");
        return new ResponseEntity<List<User>>(list,HttpStatus.OK);
    }
    @ApiOperation(value = "Get the user details from the system with specifid user id",response = User.class)
    @RequestMapping(value = "/user/id/{id}" ,method = RequestMethod.GET)

    public ResponseEntity<User> getUserById(@ApiParam(value = "User id of the user to fetch",required = true) @PathVariable("id") long id) throws ResourceNotFoundException {
        logger.info("getUserById method called to retrive user by id");
        User list= userService.getUserById(id);
        logger.info("getUserById method sending user by id");
        return new ResponseEntity<User>(list,HttpStatus.OK);
    }
    @ApiOperation(value = "Get the user details from the system with specifid user id",response = User.class)
    @RequestMapping(value = "/user/username/{username}" ,method = RequestMethod.GET)
    public ResponseEntity<User> getUserByUsername(@ApiParam(value = "User Name of the user to fetch",required = true) @PathVariable("username") String username) throws ResourceNotFoundException {
        logger.info("getUserByUsername method called to retrive user by id");
        User list= userService.getUserByUsername(username);
        logger.info("getUserByUsername method sending a retrive user by username");
        return new ResponseEntity<User>(list,HttpStatus.OK);
    }

    @ApiOperation(value="Create the user to the System",response = User.class)
    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public User createUser( @ApiParam(value = "User Information to be added",required = true) @Valid @RequestBody User user1){
        log.info("Creating the User"+user1);
        return userService.addUser(user1);

    }
    @ApiOperation(value = "User information to be Updated",response = User.class)
    @RequestMapping(value = "/user/{id}",method = RequestMethod.PUT )
    public ResponseEntity<User> updateUser(@ApiParam(value = "User id to be updated",required = true) @PathVariable(value = "id") long id,@Valid @RequestBody User user) throws ResourceNotFoundException{
        log.info("updateUser method called to update the User");
        User userUpdated=userService.updateUser(id,user);
        log.info("updateUser method has update the User");
        return ResponseEntity.ok(userUpdated);
    }
    @ApiOperation(value = "User delete from the System")
    @RequestMapping(value="/user/{id}",method = RequestMethod.DELETE)
    public Map<String, Boolean> deleteUser(@ApiParam(value = "User id to be deleted from the system",required = true ) @PathVariable(value = "id") long id) throws ResourceNotFoundException{
        log.info("deleteUser method called to update the User");
        return userService.deleteUser(id);
    }
}
