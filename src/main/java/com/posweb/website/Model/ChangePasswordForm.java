package com.posweb.website.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordForm {
    private String token;
    private String newPassword;
    private String confirmPassword;

    // getters and setters
}
