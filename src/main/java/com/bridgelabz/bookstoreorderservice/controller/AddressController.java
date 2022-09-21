package com.bridgelabz.bookstoreorderservice.controller;

import com.bridgelabz.bookstoreorderservice.dto.AddressDTO;
import com.bridgelabz.bookstoreorderservice.model.AddressModel;
import com.bridgelabz.bookstoreorderservice.service.IAddressService;
import com.bridgelabz.bookstoreorderservice.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Purpose :REST ApIs Controller
 * Version : 1.0
 *
 * @author : Aviligonda Sreenivasulu
 */
@RestController
@RequestMapping("/addressService")
public class AddressController {
    @Autowired
    IAddressService addressService;

    /**
     * Purpose :  Add Address
     *
     * @author : Aviligonda Sreenivasulu
     * @Param : token,addressDTO
     */
    @PostMapping("/addAddress")
    public ResponseEntity<Response> addAddress(@RequestHeader String token,
                                               @Valid @RequestBody AddressDTO addressDTO) {
        Response response = addressService.addAddress(token, addressDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Purpose :  Update Address
     *
     * @author : Aviligonda Sreenivasulu
     * @Param : token,addressId,addressDTO
     */
    @PutMapping("/updateAddress/{addressId}")
    public ResponseEntity<Response> updateAddress(@PathVariable Long addressId,
                                                  @RequestHeader String token,
                                                  @Valid @RequestBody AddressDTO addressDTO) {
        Response response = addressService.updateAddress(token, addressId, addressDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Purpose :  Get All Address
     *
     * @author : Aviligonda Sreenivasulu
     * @Param : token
     */
    @GetMapping("/getAllAddress")
    public ResponseEntity<List<?>> getAllAddress(@RequestHeader String token) {
        List<AddressModel> response = addressService.getAllAddress(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Purpose :  Delete Address
     *
     * @author : Aviligonda Sreenivasulu
     * @Param : token,addressId
     */
    @DeleteMapping("/deleteAddress/{addressId}")
    public ResponseEntity<Response> deleteAddress(@PathVariable Long addressId,
                                                  @RequestHeader String token) {
        Response response = addressService.deleteAddress(addressId, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
