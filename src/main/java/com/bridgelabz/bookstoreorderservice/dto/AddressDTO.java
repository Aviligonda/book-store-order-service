package com.bridgelabz.bookstoreorderservice.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
/**
 * Purpose : AddressDTO fields are Used to Create and Update Book Details
 * Version : 1.0
 *
 * @author : Aviligonda Sreenivasulu
 */
@Data
public class AddressDTO {
    @NotNull(message = "name can't be null")
    private String name;
    @NotNull(message = "address can't be null")
    private String address;
    @NotNull(message = "phoneNumber can't be null")
    private Long phoneNumber;
    @NotNull(message = "pinCode can't be null")
    private Long pinCode;
}
