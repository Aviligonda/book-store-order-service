package com.bridgelabz.bookstoreorderservice.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderServiceDTO {
    @NotNull(message = "address can't be null")
    private String address;
}
