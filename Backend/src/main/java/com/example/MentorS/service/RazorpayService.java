package com.example.MentorS.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RazorpayService {


    @Value("${rzp_key_id}")
    private String apiKey;

    @Value("${rzp_key_secret}")
    private String apiSecret;

    public String createOrder() {
        // Use Razorpay SDK to create an order
        // Return the order ID

        return "22";
    }
}
