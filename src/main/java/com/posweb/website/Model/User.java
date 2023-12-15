package com.posweb.website.Model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;
    @Column(name="full_name")
    private String fullname;
    private String email;
    private String username;
    private String password;
    private String profilePicture;
    private boolean isEnable;
}