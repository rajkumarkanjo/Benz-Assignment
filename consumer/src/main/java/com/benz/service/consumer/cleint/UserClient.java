package com.benz.service.consumer.cleint;

import com.benz.service.consumer.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "producer-service")
public interface UserClient {

    @PostMapping("/api/saveUser")
    ResponseEntity<?> saveUserRecord(@RequestBody User user, @RequestHeader Map<String,String> fileType);

    @PutMapping("/api/updateUser")
    ResponseEntity<?> updateUser(@RequestBody User user, @RequestHeader Map<String,String> fileType);

    @GetMapping("api/readUserByName")
    List<User> readUser(@PathVariable(value="name") String name);

}
