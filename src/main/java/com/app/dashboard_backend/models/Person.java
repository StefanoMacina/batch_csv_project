package com.app.dashboard_backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "First")
    private String firstname;

    @Column(name = "Last")
    private String lastname;

    @Column(name = "Age")
    private Integer age;
}
