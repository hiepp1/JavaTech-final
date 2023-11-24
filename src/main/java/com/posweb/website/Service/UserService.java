package com.posweb.website.Service;

import com.posweb.website.Model.PasswordChangeRequest;
import com.posweb.website.Model.User;
import com.posweb.website.Repository.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User authenticate(String username, String password) {
        return userRepo.findByUsernameAndPassword(username, password).orElse(null);
    }

    public boolean changePassword(PasswordChangeRequest request) {
        User user = userRepo.findByUsernameAndPassword(request.getUsername(), request.getOldPassword()).orElse(null);
        if (user != null) {
            user.setPassword(request.getNewPassword());
            userRepo.save(user);
            return true;
        }
        return false;
    }
}
