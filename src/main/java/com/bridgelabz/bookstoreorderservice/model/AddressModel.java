package com.bridgelabz.bookstoreorderservice.model;

import com.bridgelabz.bookstoreorderservice.dto.AddressDTO;
import lombok.Data;

import javax.persistence.*;
/**
 * Purpose : AddressModel Are Used Create A table and connection to Database
 * Version : 1.0
 *
 * @author : Aviligonda Sreenivasulu
 */
@Data
@Entity
@Table(name = "address")
public class AddressModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private Long userId;
    private Long phoneNumber;
    private Long pinCode;

    public AddressModel(AddressDTO addressDTO) {
        this.name = addressDTO.getName();
        this.address = addressDTO.getAddress();
        this.phoneNumber = addressDTO.getPhoneNumber();
        this.pinCode= addressDTO.getPinCode();
    }

    public AddressModel() {
    }
}
