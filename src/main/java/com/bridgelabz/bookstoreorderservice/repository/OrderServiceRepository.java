package com.bridgelabz.bookstoreorderservice.repository;

import com.bridgelabz.bookstoreorderservice.model.OrderServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Purpose : OrderServiceRepository Are Used to Store the Data into Database
 * Version : 1.0
 * @author : Aviligonda Sreenivasulu
 * */
public interface OrderServiceRepository extends JpaRepository<OrderServiceModel, Long> {
    @Query(value = "select * from orders where user_id =:userId", nativeQuery = true)
    List<OrderServiceModel> findByUserId(Long userId);

    @Query(value = "select * from orders where cancel= false ", nativeQuery = true)
    List<OrderServiceModel> findAllByCancel();
}
