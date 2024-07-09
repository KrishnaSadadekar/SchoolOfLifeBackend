package com.example.MentorS.repository;

import com.example.MentorS.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Orders,Integer> {


    @Query("select u from Orders u where u.razorId=:razorId")
    Orders findByRazorId(@Param("razorId") String razorId);
}
