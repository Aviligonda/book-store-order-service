package com.bridgelabz.bookstoreorderservice.service;

import com.bridgelabz.bookstoreorderservice.exception.UserException;
import com.bridgelabz.bookstoreorderservice.model.AddressModel;
import com.bridgelabz.bookstoreorderservice.model.OrderServiceModel;
import com.bridgelabz.bookstoreorderservice.repository.AddressRepository;
import com.bridgelabz.bookstoreorderservice.repository.OrderServiceRepository;
import com.bridgelabz.bookstoreorderservice.util.CartResponse;
import com.bridgelabz.bookstoreorderservice.util.Response;
import com.bridgelabz.bookstoreorderservice.util.TokenUtil;
import com.bridgelabz.bookstoreorderservice.util.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Purpose : OrderService to Implement the Business Logic
 * Version : 1.0
 *
 * @author : Aviligonda Sreenivasulu
 */
@Service
public class OrderService implements IOrderService {
    @Autowired
    OrderServiceRepository orderServiceRepository;
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    MailService mailService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    AddressRepository addressRepository;

    /**
     * Purpose : Implement the Logic of Place the order
     *
     * @author : Aviligonda Sreenivasulu
     * @Param :  orderServiceDTO,token,cartId
     */
    @Override
    public Response placeOrder(Long cartId, String token, Long addressId) {
        UserResponse isUserPresent = restTemplate.getForObject("http://BS-USER-SERVICE:8080/userService/userVerification/" + token, UserResponse.class);
        if (isUserPresent.getStatusCode() == 200) {
            CartResponse isCartPresent = restTemplate.getForObject("http://BS-CART-SERVICE:8082/cartService/verifyCartItem/" + cartId, CartResponse.class);
            if (isUserPresent.getStatusCode() == 200) {
                if (isUserPresent.getObject().getId() == isCartPresent.getObject().getUserId()) {
                    OrderServiceModel orderServiceModel = new OrderServiceModel();
                    orderServiceModel.setQuantity(isCartPresent.getObject().getQuantity());
                    orderServiceModel.setPrice(isCartPresent.getObject().getTotalPrice());
                    orderServiceModel.setOrderDate(LocalDateTime.now());
                    orderServiceModel.setBookId(isCartPresent.getObject().getBookId());
                    orderServiceModel.setUserId(isUserPresent.getObject().getId());
                    orderServiceModel.setCancel(false);
                    Optional<AddressModel> isAddressPresent = addressRepository.findById(addressId);
                    if (isAddressPresent.isPresent()) {
                        if (isAddressPresent.get().getUserId() == isUserPresent.getObject().getId()) {
                            orderServiceModel.setAddress(isAddressPresent.get());
                        } else {
                            throw new UserException(400, "Address UserId and UserId Did't match");
                        }
                    } else {
                        throw new UserException(400, "Address Not Found with this Id");
                    }
                    orderServiceRepository.save(orderServiceModel);
                    String body = "Your Order Placed with Order id is :" + orderServiceModel.getId();
                    String subject = "Successfully Placed Order ";
                    mailService.send(isUserPresent.getObject().getEmailId(), body, subject);
                    return new Response(200, "Success", orderServiceModel);
                }
                throw new UserException(400, "No Cart Found with this UserId");
            }
            throw new UserException(400, "No Cart item Found with this id");
        }
        return null;
    }

    /**
     * Purpose : Implement the Logic of Cancel the Order
     *
     * @author : Aviligonda Sreenivasulu
     * @Param :  orderId,token
     */
    @Override
    public Response cancelOrder(Long orderId, String token) {
        UserResponse isUserPresent = restTemplate.getForObject("http://BS-USER-SERVICE:8080/userService/userVerification/" + token, UserResponse.class);
        if (isUserPresent.getStatusCode() == 200) {
            Optional<OrderServiceModel> isOrderPresent = orderServiceRepository.findById(orderId);
            if (isOrderPresent.isPresent()) {
                if (isOrderPresent.get().getUserId() == isUserPresent.getObject().getId()) {
                    isOrderPresent.get().setCancel(true);
                    orderServiceRepository.save(isOrderPresent.get());
                    String body = "Your Order Cancel with Order id is :" + isOrderPresent.get().getId();
                    String subject = "Successfully Cancel Order ";
                    mailService.send(isUserPresent.getObject().getEmailId(), body, subject);
                    return new Response(200, "Success", isOrderPresent.get());
                }
                throw new UserException(400, "No Order present with this UserId");
            }
            throw new UserException(400, "No Order found with this id");
        }
        return null;
    }

    /**
     * Purpose : Implement the Logic of Get All Orders for User
     *
     * @author : Aviligonda Sreenivasulu
     * @Param :  token
     */
    @Override
    public List<OrderServiceModel> getAllOrdersForUser(String token) {
        UserResponse isUserPresent = restTemplate.getForObject("http://BS-USER-SERVICE:8080/userService/userVerification/" + token, UserResponse.class);
        if (isUserPresent.getStatusCode() == 200) {
            List<OrderServiceModel> isOrderPresent = orderServiceRepository.findByUserId(isUserPresent.getObject().getId());
            if (isOrderPresent.size() > 0) {
                return isOrderPresent;
            }
            throw new UserException(400, "No order Found");
        }
        throw new UserException(400, "No Orders Found with this userId");
    }

    /**
     * Purpose : Implement the Logic of Get All Orders
     *
     * @author : Aviligonda Sreenivasulu
     * @Param :
     */
    @Override
    public List<OrderServiceModel> getAllOrders() {
        List<OrderServiceModel> isOrderPresent = orderServiceRepository.findAllByCancel();
        if (isOrderPresent.size() > 0) {
            return isOrderPresent;
        }
        throw new UserException(400, "No order Found");
    }
}
