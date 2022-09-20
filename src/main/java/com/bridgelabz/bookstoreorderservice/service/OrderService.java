package com.bridgelabz.bookstoreorderservice.service;

import com.bridgelabz.bookstoreorderservice.dto.OrderServiceDTO;
import com.bridgelabz.bookstoreorderservice.exception.UserException;
import com.bridgelabz.bookstoreorderservice.model.OrderServiceModel;
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

    /**
     * Purpose : Implement the Logic of Place the order
     *
     * @author : Aviligonda Sreenivasulu
     * @Param :  orderServiceDTO,token,cartId
     */
    @Override
    public Response placeOrder(Long cartId, String token, OrderServiceDTO orderServiceDTO) {
        UserResponse isUserPresent = restTemplate.getForObject("http://BS-USER-SERVICE:8080/userService/userVerification/" + token, UserResponse.class);
        if (isUserPresent.getStatusCode() == 200) {
            CartResponse isCartPresent = restTemplate.getForObject("http://BS-CART-SERVICE:8082/cartService/verifyCartItem/" + cartId, CartResponse.class);
            if (isUserPresent.getStatusCode() == 200) {
                if (isUserPresent.getObject().getId() == isCartPresent.getObject().getUserId()) {
                    OrderServiceModel orderServiceModel = new OrderServiceModel(orderServiceDTO);
                    orderServiceModel.setQuantity(isCartPresent.getObject().getQuantity());
                    orderServiceModel.setPrice(isCartPresent.getObject().getTotalPrice());
                    orderServiceModel.setOrderDate(LocalDateTime.now());
                    orderServiceModel.setBookId(isCartPresent.getObject().getBookId());
                    orderServiceModel.setUserId(isUserPresent.getObject().getId());
                    orderServiceModel.setCancel(false);
                    orderServiceRepository.save(orderServiceModel);
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
            Optional<OrderServiceModel> isUserIdPresent = orderServiceRepository.findByUserId(isUserPresent.getObject().getId());
            if (isUserIdPresent.isPresent()) {
                List<OrderServiceModel> isOrderPresent = orderServiceRepository.findAll();
                if (isOrderPresent.size() > 0) {
                    return isOrderPresent;
                }
                throw new UserException(400, "No order Found");
            }
            throw new UserException(400, "No Orders Found with this userId");
        }
        return null;
    }

    /**
     * Purpose : Implement the Logic of Get All Orders
     *
     * @author : Aviligonda Sreenivasulu
     * @Param :
     */
    @Override
    public List<OrderServiceModel> getAllOrders() {
        List<OrderServiceModel> isOrderPresent = orderServiceRepository.findAll();
        if (isOrderPresent.size() > 0) {
            return isOrderPresent;
        }
        throw new UserException(400, "No order Found");
    }
}
