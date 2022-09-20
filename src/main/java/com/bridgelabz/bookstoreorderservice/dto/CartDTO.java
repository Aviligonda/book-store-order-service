package com.bridgelabz.bookstoreorderservice.dto;

import lombok.Data;

import javax.persistence.Lob;
import java.time.LocalDateTime;
/**
 * Purpose : CartDTO fields are Used Retrieve the data from Book service
 * Version : 1.0
 *
 * @author : Aviligonda Sreenivasulu
 */
@Data
public class CartDTO {
    private Long id;
    private long userId;
    private long bookId;
    private long quantity;
    private long totalPrice;
}
