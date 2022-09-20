package com.bridgelabz.bookstoreorderservice.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
/**
 * Purpose : UserDTO fields are Used Retrieve the data from User service
 * Version : 1.0
 *
 * @author : Aviligonda Sreenivasulu
 */

@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String emailId;
    private String password;
    private LocalDate dateOfBirth;
    private String kyc;
    private Long otp;
    private Boolean verify;
    private Date purchaseDate;
    private Date expireDate;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

}
