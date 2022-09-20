package com.bridgelabz.bookstoreorderservice.util;

import com.bridgelabz.bookstoreorderservice.dto.CartDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Purpose :Return Cart Status
 * Version : 1.0
 *
 * @author : Aviligonda Sreenivasulu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private int statusCode;
    private String statusMessage;
    private CartDTO object;
}
