package com.bridgelabz.bookstoreorderservice.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
/**
 * Purpose : OrderServiceModel Are Used Create A table and connection to Database
 * Version : 1.0
 *
 * @author : Aviligonda Sreenivasulu
 */
@Entity
@Data
@Table(name = "orders")
public class OrderServiceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long price;
    private long userId;
    @OneToOne
    private AddressModel address;
    private long bookId;
    private boolean cancel;
    private long quantity;
    private LocalDateTime orderDate;


    public OrderServiceModel() {
    }
}
