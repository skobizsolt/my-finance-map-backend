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

/**
 * Implementation of User controller.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * List all users email and username.
     *
     * @return 200 OK.
     */
    @GetMapping
    @Operation(summary = "List all users data")
    public ResponseEntity<List<MinimalUserDto>> getUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    /**
     * List all user specific data without critical information.
     *
     * @param userId refers to the user's identity.
     * @return with UserDto, containing User, Profile and Address data.
     */
    @GetMapping(value = "/{userId}/profile")
    @Operation(summary = "List all user specific data to profile page")
    public ResponseEntity<UserDto> getAllUserData(@PathVariable final Long userId) {
        return ResponseEntity.ok().body(userService.getUserById(userId));
    }

    /**
     * Creates a new user.
     *
     * @param data provides all essential user data.
     * @return with MinimalUserDto, containing the created entity's data.
     */
    @PostMapping(value = "/register")
    @Operation(summary = "Create new User")
    public ResponseEntity<MinimalUserDto> createUser(@Valid @RequestBody final CreateUserDto data) {
        return ResponseEntity.ok().body(userService.createUser(data));
    }

    /**
     * Deletes user from repository.
     *
     * @param userId for selecting what user should be deleted.
     * @return 200 OK.
     */
    @DeleteMapping(value = "/{userId}")
    @Operation(summary = "Delete user")
    public ResponseEntity<Void> deleteUser(@PathVariable final Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
