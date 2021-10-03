package com.benz.service.consumer.controllers;

import com.benz.service.consumer.cleint.UserClient;
import com.benz.service.consumer.model.User;
import com.benz.service.consumer.model.UserResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author RAJ
 */

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class UserConsumerController {

   @Autowired
   public UserClient userClient;

    @GetMapping("/testUser")
    public String test(){
        return "Inside userController !";
    }
     @PostMapping("/store")
     public ResponseEntity<?> storeUser(@RequestBody User user ,
                                           @RequestHeader Map<String, String> headers) {

         log.info("Inside UserConsumer Controller : save");

         if (Objects.isNull(user)) {
             log.info("user body is not present !");
             return new ResponseEntity<>("Failure", HttpStatus.INTERNAL_SERVER_ERROR);
         }
         ResponseEntity<?> success = userClient.saveUserRecord(user, headers);

         if (success.getStatusCode() == HttpStatus.CREATED){
            return new ResponseEntity<>("Successful", HttpStatus.CREATED);
         }
          else {
             return new ResponseEntity<>("Failure", HttpStatus.INTERNAL_SERVER_ERROR);
         }
     }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User user ,
                                        @RequestHeader Map<String, String> headers) {

        log.info("Inside UserConsumer Controller : update");

        if (Objects.isNull(user)) {
            log.info("user body not present !");
            return new ResponseEntity<>("Failure", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        ResponseEntity<?> success = userClient.updateUser(user, headers);

        if (success.getStatusCode() == HttpStatus.OK){
            return new ResponseEntity<>("Successful", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Failure", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/read")
    public UserResponseDto readUser(@PathVariable(value="name") String name ) {

        log.info("Inside UserConsumer Controller : read");

        if (StringUtils.isNotBlank(name) && StringUtils.isNotEmpty(name)) {
            log.info("no record found");
            return UserResponseDto.builder().status(404).message("name attribute is missing ").body(null).build();
        }
            List<User> userRecord = userClient.readUser(name);

        if (!Objects.isNull(userRecord) && !userRecord.isEmpty()){

            return UserResponseDto.builder().status(200).message("Successfully").body(userRecord).build();
        }else{
            log.info("no record found");
            return UserResponseDto.builder().status(404).message("Record Not Found").body(userRecord).build();
        }

    }
}
