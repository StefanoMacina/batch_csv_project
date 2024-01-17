package com.app.dashboard_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Order {

    @Id
    @Column(
            name = "order_id"
    )
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
