package com.javahunter.multipleloggers.controller;

import com.javahunter.multipleloggers.dto.RequestDto;
import com.javahunter.multipleloggers.dto.ResponseDto;
import com.javahunter.multipleloggers.entity.User;
import com.javahunter.multipleloggers.service.UserService;
import com.javahunter.multipleloggers.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger serverLogger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@RequestBody RequestDto requestDto){
        serverLogger.info("Request hitting save User controller");
        return ResponseEntity.ok()
                .body(userService.saveUser(requestDto));
    }

    @GetMapping("/get-by-email/{email}")
    public ResponseEntity<ResponseDto> getUserByEmail(@PathVariable("email") String email){
        serverLogger.info("Request hitting get User by Email controller");
        return ResponseEntity.ok()
                .body(userService.getUserByEmail(email));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ResponseDto>> getAllUsers(){
        serverLogger.info("Request hitting get all users controller");
        return ResponseEntity.ok()
                .body(userService.getAllUsers());
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody RequestDto requestDto){
        serverLogger.info("Request hitting update user controller");
        boolean isUpdated = userService.updateUser(requestDto);
        if(!isUpdated)
            return ResponseEntity.internalServerError()
                    .body("Update failed");
        return ResponseEntity.ok()
                .body("Update successful");
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable("email") String email){
        serverLogger.info("Request hitting delete user controller");
        boolean isDeleted = userService.userDeleted(email);
        if(!isDeleted)
            return ResponseEntity.internalServerError()
                    .body("Delete operation failed");
        return ResponseEntity.ok()
                .body("Delete operation was successful");
    }
}
