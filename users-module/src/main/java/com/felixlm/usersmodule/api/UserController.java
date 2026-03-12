package com.felixlm.usersmodule.api;

import com.felixlm.usersmodule.api.dto.CreateUserRequest;
import com.felixlm.usersmodule.api.dto.UserResponse;
import com.felixlm.usersmodule.application.UserService;
import com.felixlm.usersmodule.domain.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public UserResponse createUser(@RequestBody CreateUserRequest request){
        User user = userService.createUser(
                request.name(),
                request.email()
        );

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    @GetMapping
    public List<UserResponse> getUsers(){
        return userService.getUsers()
                .stream()
                .map(u -> new UserResponse(
                        u.getId(),
                        u.getName(),
                        u.getEmail()
                ))
                .toList();
    }
}
