package com.bridgelabz.bookstoreorderservice.service;

import com.bridgelabz.bookstoreorderservice.dto.AddressDTO;
import com.bridgelabz.bookstoreorderservice.model.AddressModel;
import com.bridgelabz.bookstoreorderservice.util.Response;

import java.util.List;
/**
 * Purpose : IAddressService to Show The all APIs
 * Version : 1.0
 *
 * @author : Aviligonda Sreenivasulu
 */
public interface IAddressService {
    Response addAddress(String token, AddressDTO addressDTO);

    Response updateAddress(String token, Long addressId, AddressDTO addressDTO);

    List<AddressModel> getAllAddress(String token);

    Response deleteAddress(Long addressId, String token);
}
