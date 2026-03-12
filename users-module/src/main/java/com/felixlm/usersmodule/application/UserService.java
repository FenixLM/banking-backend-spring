package com.felixlm.usersmodule.application;

import com.felixlm.usersmodule.domain.User;
import com.felixlm.usersmodule.domain.UserRepository;

import java.util.List;
import java.util.UUID;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User createUser(String name, String email){

            User user = new User(
                UUID.randomUUID(),
                name,
                email
        );

        userRepository.save(user);

        return user;
    }

    public List<User> getUsers(){
       return userRepository.findAll();
    }

}
