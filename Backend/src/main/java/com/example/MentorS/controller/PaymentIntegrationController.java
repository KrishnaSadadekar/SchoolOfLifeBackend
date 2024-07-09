package com.example.MentorS.controller;

import com.example.MentorS.models.Course;
import com.example.MentorS.models.Orders;
import com.example.MentorS.repository.CourseRepository;
import com.example.MentorS.repository.OrderRepository;
import com.example.MentorS.service.Cservice;
import com.example.MentorS.service.RazorpayVerificationService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class PaymentIntegrationController {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    private RazorpayVerificationService razorpayVerificationService;

    @Autowired
    Cservice cservice;
    @Value("${rzp_key_id}")
    private String keyId;

    @Value("${rzp_key_secret}")
    private String secret;

    @PostMapping("/create-order/{id}")
    public ResponseEntity<?> createOrder(@PathVariable("id") Integer id,@RequestBody Orders order) throws RazorpayException {
        System.out.println("In loop-----");
        try {
            System.out.println("Email->:"+order.getEmail());
            Course course = courseRepository.findById(id).get();
            order.setCourse(course);
            System.out.println("The course Name is " + course.getName());
            Orders rs = cservice.placeOrder(order);


//        -RazorPay Functionality
            RazorpayClient razorpayClient = new RazorpayClient(keyId, secret);

            JSONObject orderRequestParams = new JSONObject();
            System.out.println("Amount-> -------------");
            orderRequestParams.put("amount", (course.getPrice()*100)); // Amount in paise
            orderRequestParams.put("currency", "INR");
            // Add other necessary parameters

            Order razorder = razorpayClient.orders.create(orderRequestParams);
            System.out.println("Data ->" + razorder.toString());
            String rId=razorder.get("id");
            order.setRazorId(rId);
            order.setCourseFees((int)course.getPrice());
            orderRepository.save(order);
//            Minus one seat
            course.setSeats(course.getSeats()-1);
            courseRepository.save(course);
            System.out.println("The Razor Id : "+rId);

            return ResponseEntity.ok(order);
        } catch (Exception e) {
            System.out.println("Error:->"+e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/payment-callback")
    public void handlePaymentCallback(@RequestParam String orderId, @RequestParam String paymentId,@RequestParam String signature) {
        // Implement logic to verify and handle the payment callback
        // Check Razorpay documentation for details on verifying the webhook signature
        System.out.println("In call Back! "+orderId+"->>"+paymentId);
        Orders orders=orderRepository.findByRazorId(orderId);
        orders.setStatus("OrderPlaced: "+paymentId);
        orderRepository.save(orders);

    }


}
