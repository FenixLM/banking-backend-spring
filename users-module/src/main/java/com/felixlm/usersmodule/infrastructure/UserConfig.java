package com.felixlm.usersmodule.infrastructure;

import com.felixlm.usersmodule.application.UserService;
import com.felixlm.usersmodule.domain.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserService(userRepository);
    }
}
