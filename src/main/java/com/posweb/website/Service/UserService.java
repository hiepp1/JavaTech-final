package com.posweb.website.Service;

import com.posweb.website.Model.PasswordChangeRequest;
import com.posweb.website.Model.User;
import com.posweb.website.Repository.UserRepo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;

    public enum ChangePasswordResult {
        SUCCESS,
        SAME_PASSWORD,
        USER_NOT_FOUND,
        FAILURE
    }


    public User save(User user) {
        return userRepo.save(user);
    }

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

//    public ChangePasswordResult changePassword(PasswordChangeRequest request)
//    {
//        User user = userRepo.findByUsername(request.getUsername());
//
//        if (user == null) {
//            return ChangePasswordResult.USER_NOT_FOUND;
////        } else if (user.getPassword().equals(request.getOldPassword())) {
//        } else if (BCrypt.checkpw(password, passwordOld)) {
//            if (request.getNewPassword().equals(request.getOldPassword())) {
//                return ChangePasswordResult.SAME_PASSWORD;
//            } else {
//                user.setPassword(request.getNewPassword());
//                userRepo.save(user);
//                return ChangePasswordResult.SUCCESS;
//            }
//        } else {
//            return ChangePasswordResult.FAILURE;
//        }
//    }

    public ChangePasswordResult changePassword(PasswordChangeRequest request) {
        User user = userRepo.findByUsername(request.getUsername());

        if (user == null) {
            return ChangePasswordResult.USER_NOT_FOUND;
        }

        String currentPassword = request.getOldPassword();
        String hashedCurrentPassword = user.getPassword();

        if (BCrypt.checkpw(currentPassword, hashedCurrentPassword)) {
            String newPassword = request.getNewPassword();
            String hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(10));

            if (BCrypt.checkpw(newPassword, hashedCurrentPassword)) {
                return ChangePasswordResult.SAME_PASSWORD;
            } else {
                user.setPassword(hashedNewPassword);
                userRepo.save(user);
                return ChangePasswordResult.SUCCESS;
            }
        } else {
            return ChangePasswordResult.FAILURE;
        }
    }

    public ChangePasswordResult changePasswordForNewSale(User user, String newPassword)
    {
        user.setPassword(newPassword);
        userRepo.save(user);
        return ChangePasswordResult.SUCCESS;
    }

}
