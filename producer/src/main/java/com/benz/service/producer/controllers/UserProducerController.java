package com.benz.service.producer.controllers;

import lombok.extern.slf4j.Slf4j;
import com.benz.service.producer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.benz.service.producer.services.UserService;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author RAJ
 */

@Slf4j
@RestController
@RequestMapping("/api")
public class UserProducerController {

    @Autowired
    UserService userService;

    @GetMapping("/testUser")
    public String test(){

        return "Inside userController !";
    }
     @PostMapping("/saveUser")
     public ResponseEntity<?> saveUserRecord(@RequestBody User user ,
                                   @RequestHeader Map<String, String> headers) throws IOException {

         log.info("Inside producer User Controller : save");

         boolean success = userService.saveUser(user, headers);

         if (success){
            return new ResponseEntity<>("Successful", HttpStatus.CREATED);
         }
          else {
             return new ResponseEntity<>("Failure", HttpStatus.INTERNAL_SERVER_ERROR);
         }
     }

    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUserRecord(@RequestBody User user ,
                              @RequestHeader Map<String, String> headers) throws IOException, JAXBException {

        log.info("Inside updateUser producer Controller : update");

        boolean success = userService.updateUser(user, headers);

        if (success){
            return new ResponseEntity<>("Successful", HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>("Failure", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/readUserByName")
    public List<User> readUser(@PathVariable(value="name") String name ) throws IOException, JAXBException {

        log.info("Inside readUserByName producer Controller : read");

        List<User> userRecord= userService.readUser(name);

        if (Objects.isNull(userRecord) || userRecord.isEmpty()) {
            log.info("no record found for the user !");
        }
        return userRecord;
    }
}


