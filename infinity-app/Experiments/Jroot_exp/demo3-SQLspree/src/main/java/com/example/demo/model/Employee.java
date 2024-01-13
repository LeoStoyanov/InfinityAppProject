package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
//entity tells us that is is an entity
@Table(name="employees")
public class Employee
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;


}
