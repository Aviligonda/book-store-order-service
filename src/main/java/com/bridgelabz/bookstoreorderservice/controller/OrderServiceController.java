package com.bridgelabz.bookstoreorderservice.controller;


import com.bridgelabz.bookstoreorderservice.model.OrderServiceModel;
import com.bridgelabz.bookstoreorderservice.service.IOrderService;
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
@RequestMapping("/orderService")
public class OrderServiceController {
    @Autowired
    IOrderService orderService;

    /**
     * Purpose :  Place Order
     *
     * @author : Aviligonda Sreenivasulu
     * @Param : token,cartId,orderServiceDTO
     */
    @PutMapping("/placeOrder/{cartId}")
    public ResponseEntity<Response> placeOrder(@PathVariable Long cartId,
                                               @RequestHeader String token,
                                               @RequestParam Long addressId) {
        Response response = orderService.placeOrder(cartId, token, addressId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Purpose :  Cancel order
     *
     * @author : Aviligonda Sreenivasulu
     * @Param : token,OrderId
     */
    @PutMapping("/cancelOrder/{orderId}")
    public ResponseEntity<Response> cancelOrder(@PathVariable Long orderId,
                                                @RequestHeader String token) {
        Response response = orderService.cancelOrder(orderId, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Purpose :  Get All Orders for Particular User
     *
     * @author : Aviligonda Sreenivasulu
     * @Param : token
     */
    @GetMapping("/getAllOrdersForUser")
    public ResponseEntity<List<?>> getAllOrdersForUser(@RequestHeader String token) {
        List<OrderServiceModel> response = orderService.getAllOrdersForUser(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Purpose :  Get All Orders
     *
     * @author : Aviligonda Sreenivasulu
     * @Param :
     */
    @GetMapping("/getAllOrders")
    public ResponseEntity<List<?>> getAllOrders() {
        List<OrderServiceModel> response = orderService.getAllOrders();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
