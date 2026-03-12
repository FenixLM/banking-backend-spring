package com.felixlm.usersmodule.infrastructure;


import com.felixlm.usersmodule.domain.User;
import com.felixlm.usersmodule.domain.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {}

@Repository
@Primary
public class JpaUserRepository implements UserRepository {

    private final UserJpaRepository repository;

    public JpaUserRepository(UserJpaRepository repository){
        this.repository = repository;
    }


    @Override
    public void save(User user) {
        repository.save(new UserEntity(
                user.getId(),
                user.getName(),
                user.getEmail()
        ));
    }

    @Override
    public List<User> findAll() {
       return repository.findAll()
               .stream()
               .map(e -> new User(
                       e.getId(),
                       e.getName(),
                       e.getEmail()))
               .collect(Collectors.toList());
    }
}
