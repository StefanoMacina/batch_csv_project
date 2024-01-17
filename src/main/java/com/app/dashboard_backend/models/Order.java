package com.app.dashboard_backend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter @Table(name = "[order]")
public class Order {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String product_name;

    @Column
    private Integer quantity;

    @Column
    private float price;

    @Column
    private String customer_name;

    @Column
    private  String customer_email;

}
