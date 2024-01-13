package com.example.demo;

import javax.persistence.*;



@Entity
//entity tells us that is is an entity
@Table(name="Users")
public class Users
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    
}

