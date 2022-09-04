package com.example.demo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(generator = "seq_user")
//    @SequenceGenerator(name = "seq_user", sequenceName = "SEQ_USER", allocationSize = 1)
    private Long id;

    private String username;

    private String password;

    private String email;

    private String phoneNumber;

    private String birthDay;

    private String fullName;

    private String avatar;

    private String backGround;

    private String address;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role")
    private List<Role> roles;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String email, String phoneNumber, String birthDay) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
    }
}
