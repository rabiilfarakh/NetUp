package com.example.NetUp.user.web;

import com.example.NetUp.user.dtos.UserDTOReq;
import com.example.NetUp.user.dtos.UserDTORes;
import com.example.NetUp.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTORes> createUser(@RequestBody UserDTOReq userDTOReq) {
        UserDTORes createdUser = userService.createUser(userDTOReq);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTORes> getUserById(@PathVariable Long id) {
        UserDTORes user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UserDTORes>> getAllUsers() {
        List<UserDTORes> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTORes> patchUser(
            @PathVariable Long id,
            @RequestBody UserDTOReq userDTOReq
    ){
        UserDTORes updatedUser = userService.updateUser(id, userDTOReq);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
