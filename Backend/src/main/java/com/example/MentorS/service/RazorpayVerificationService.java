package com.example.MentorS.service;

import com.example.MentorS.models.RazorPay;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RazorpayVerificationService {

    @Value("${rzp_key_id}")
    private String keyId;

    @Value("${rzp_key_secret}")
    private String secret;


    public boolean verifyPayment(String paymentId, String orderId, String signature) throws RazorpayException {
        try {
            RazorpayClient razorpayClient = new RazorpayClient(keyId, secret);

            String payload = orderId + "|" + paymentId;
//            return razorpayClient.Utility.verifyPaymentSignature(payload, signature)

               return true;
        } catch (RazorpayException e) {
            // Handle exception
            e.printStackTrace();
            return false;
        }
    }
}
