package com.example.kbfinal.controller;

import com.example.kbfinal.entity.User;
import com.example.kbfinal.service.UserService;
import com.example.kbfinal.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    // user 정보를 입력, 삭제, 수정하는 API 생성
    // user 정보를 입력하는 API 생성
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userService.registerUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // user 정보를 삭제하는 API 생성
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean isRemoved = userService.deleteUser(id);
        if (!isRemoved) {
            throw new UserNotFoundException("User not found with id " + id);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // user 정보를 수정하는 API 생성
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        if (updatedUser == null) {
            throw new UserNotFoundException("User not found with id " + id);
        }
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // 전체 user List를 조회하는 api 생성
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // 전체 user 의 숫자를 조회하는 api 생성
    @GetMapping("/count")
    public ResponseEntity<Long> getUserCount() {
        Long count = userService.getUserCount();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
