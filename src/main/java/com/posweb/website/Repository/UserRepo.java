package com.posweb.website.Repository;

import com.posweb.website.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("userRepository")
public interface UserRepo extends JpaRepository<User, Integer> {

    User findByUsernameAndPassword(String username, String password);
    User findByUsername(String username);
    List<User> findByRole(String role);
    User findUserById(int id);
    User findByEmailIgnoreCase(String email);
}
