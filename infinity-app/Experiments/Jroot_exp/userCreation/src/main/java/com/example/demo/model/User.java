package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;

/**
 * This class just holds basic information about the user
 * tentatively this is all the information we need to set
 * up a login
 * @author jroot
 */
@Data
@Entity
@Table(name = "Users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column (name = "username", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
}
