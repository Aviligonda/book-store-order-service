package com.bridgelabz.bookstoreorderservice.util;

import com.bridgelabz.bookstoreorderservice.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Purpose :Return User Status
 * Version : 1.0
 *
 * @author : Aviligonda Sreenivasulu
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {
    private int statusCode;
    private String statusMessage;
    private UserDTO object;
}
