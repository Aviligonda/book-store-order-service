package com.bridgelabz.bookstoreorderservice.repository;

import com.bridgelabz.bookstoreorderservice.model.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
/**
 * Purpose : AddressRepository Are Used to Store the Data into Database
 * Version : 1.0
 * @author : Aviligonda Sreenivasulu
 * */

public interface AddressRepository extends JpaRepository<AddressModel, Long> {
    @Query(value = "select * from address where user_id =:userId", nativeQuery = true)
    List<AddressModel> findAllByUserId(Long userId);
}
