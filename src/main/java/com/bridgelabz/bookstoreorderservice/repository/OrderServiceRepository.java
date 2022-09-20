package com.bridgelabz.bookstoreorderservice.repository;

import com.bridgelabz.bookstoreorderservice.model.OrderServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
/**
 * Purpose : OrderServiceRepository Are Used to Store the Data into Database
 * Version : 1.0
 * @author : Aviligonda Sreenivasulu
 * */
public interface OrderServiceRepository extends JpaRepository<OrderServiceModel, Long> {
    Optional<OrderServiceModel> findByUserId(Long id);

//    @Query(value = "select * from orders where cancel= true ", nativeQuery = true)
//    List<OrderServiceModel> findAllByCancel();
}
