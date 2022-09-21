package com.bridgelabz.bookstoreorderservice.service;

import com.bridgelabz.bookstoreorderservice.model.OrderServiceModel;
import com.bridgelabz.bookstoreorderservice.util.Response;

import java.util.List;
/**
 * Purpose : IOrderService to Show The all APIs
 * Version : 1.0
 *
 * @author : Aviligonda Sreenivasulu
 */
public interface IOrderService {
    Response placeOrder(Long cartId, String token, Long addressId);

    Response cancelOrder(Long orderId, String token);

    List<OrderServiceModel> getAllOrdersForUser(String token);

    List<OrderServiceModel> getAllOrders();
}
