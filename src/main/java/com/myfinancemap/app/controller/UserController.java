package com.myfinancemap.app.controller;

import com.myfinancemap.app.dto.CreateUserDto;
import com.myfinancemap.app.dto.MinimalUserDto;
import com.myfinancemap.app.dto.UserDto;
import com.myfinancemap.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "List all users data")
    public ResponseEntity<List<MinimalUserDto>> getUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping(value = "/{userId}/profile")
    @Operation(summary = "List all user specific data to profile page")
    public ResponseEntity<UserDto> getAllUserData(@PathVariable final Long userId) {
        return ResponseEntity.ok().body(userService.getUserById(userId));
    }

    @PostMapping(value = "/register")
    @Operation(summary = "Create new User")
    public ResponseEntity<MinimalUserDto> createUser(@Valid @RequestBody final CreateUserDto data) {
        return ResponseEntity.ok().body(userService.createUser(data));
    }

    @DeleteMapping(value = "/{userId}")
    @Operation(summary = "Create new User")
    public ResponseEntity<Void> updateUser(@PathVariable final Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
