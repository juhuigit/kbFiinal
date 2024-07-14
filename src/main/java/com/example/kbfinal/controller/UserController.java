package com.example.kbfinal.controller;

import org.springframework.stereotype.Controller;

@Controller
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // user 정보를 수정하는 API 생성
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        if (updatedUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
