package com.posweb.website.Repository;

import com.posweb.website.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("userRepository")
public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findByUsernameAndPassword(String username, String password);
    Optional<User> findByUsername(String username);

    Optional<User> getUserById(int id);
    User findByEmailIgnoreCase(String email);
}
