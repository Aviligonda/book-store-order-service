package com.bridgelabz.bookstoreorderservice.service;

import com.bridgelabz.bookstoreorderservice.dto.AddressDTO;
import com.bridgelabz.bookstoreorderservice.exception.UserException;
import com.bridgelabz.bookstoreorderservice.model.AddressModel;
import com.bridgelabz.bookstoreorderservice.repository.AddressRepository;
import com.bridgelabz.bookstoreorderservice.repository.OrderServiceRepository;
import com.bridgelabz.bookstoreorderservice.util.Response;
import com.bridgelabz.bookstoreorderservice.util.TokenUtil;
import com.bridgelabz.bookstoreorderservice.util.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

/**
 * Purpose : AddressService to Implement the Business Logic
 * Version : 1.0
 *
 * @author : Aviligonda Sreenivasulu
 */

@Service
public class AddressService implements IAddressService {
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    MailService mailService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    AddressRepository addressRepository;

    /**
     * Purpose : Implement the Logic of Add Address
     *
     * @author : Aviligonda Sreenivasulu
     * @Param :  addressDTO,token
     */
    @Override
    public Response addAddress(String token, AddressDTO addressDTO) {
        UserResponse isUserPresent = restTemplate.getForObject("http://BS-USER-SERVICE:8080/userService/userVerification/" + token, UserResponse.class);
        if (isUserPresent.getStatusCode() == 200) {
            AddressModel addressModel = new AddressModel(addressDTO);
            addressModel.setUserId(isUserPresent.getObject().getId());
            addressRepository.save(addressModel);
            return new Response(200, "Success", addressModel);
        }
        return null;
    }

    /**
     * Purpose : Implement the Logic of Update Address
     *
     * @author : Aviligonda Sreenivasulu
     * @Param :  addressDTO,token,addressId
     */
    @Override
    public Response updateAddress(String token, Long addressId, AddressDTO addressDTO) {
        UserResponse isUserPresent = restTemplate.getForObject("http://BS-USER-SERVICE:8080/userService/userVerification/" + token, UserResponse.class);
        if (isUserPresent.getStatusCode() == 200) {
            Optional<AddressModel> isAddressPresent = addressRepository.findById(addressId);
            if (isAddressPresent.isPresent()) {
                if (isAddressPresent.get().getUserId() == isUserPresent.getObject().getId()) {
                    isAddressPresent.get().setName(addressDTO.getName());
                    isAddressPresent.get().setPinCode(addressDTO.getPinCode());
                    isAddressPresent.get().setPhoneNumber(addressDTO.getPhoneNumber());
                    isAddressPresent.get().setAddress(addressDTO.getAddress());
                    addressRepository.save(isAddressPresent.get());
                    return new Response(200, "Success", isAddressPresent.get());
                }
                throw new UserException(400, "Did't match with userId and Address UserId");
            }
            throw new UserException(400, "Address Not found with this ID");
        }
        return null;
    }

    /**
     * Purpose : Implement the Logic of Get all Address For user
     *
     * @author : Aviligonda Sreenivasulu
     * @Param :  token
     */
    @Override
    public List<AddressModel> getAllAddress(String token) {
        UserResponse isUserPresent = restTemplate.getForObject("http://BS-USER-SERVICE:8080/userService/userVerification/" + token, UserResponse.class);
        if (isUserPresent.getStatusCode() == 200) {
            Long userId = tokenUtil.decodeToken(token);
            List<AddressModel> isAddressPresent = addressRepository.findAllByUserId(isUserPresent.getObject().getId());
            if (isAddressPresent.size() > 0) {
                return isAddressPresent;
            }
            throw new UserException(400, "No Address Found");
        }
        return null;
    }

    /**
     * Purpose : Implement the Logic of Delete Address
     *
     * @author : Aviligonda Sreenivasulu
     * @Param :  addressId,token
     */
    @Override
    public Response deleteAddress(Long addressId, String token) {
        UserResponse isUserPresent = restTemplate.getForObject("http://BS-USER-SERVICE:8080/userService/userVerification/" + token, UserResponse.class);
        if (isUserPresent.getStatusCode() == 200) {
            Optional<AddressModel> isAddressPresent = addressRepository.findById(addressId);
            if (isAddressPresent.isPresent()) {
                if (isAddressPresent.get().getUserId() == isUserPresent.getObject().getId()) {
                    addressRepository.delete(isAddressPresent.get());
                    return new Response(200, "Success", isAddressPresent.get());
                }
                throw new UserException(400, "Did't match with userId and Address UserId");
            }
            throw new UserException(400, "Address Not found with this ID");
        }
        return null;
    }
}
