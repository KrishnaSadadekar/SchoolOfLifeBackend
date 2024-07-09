package com.example.MentorS.service;


import com.example.MentorS.models.Course;
import com.example.MentorS.models.Orders;
import com.example.MentorS.models.QueryT;
import com.example.MentorS.models.User;
import com.example.MentorS.repository.CourseRepository;
import com.example.MentorS.repository.OrderRepository;
import com.example.MentorS.repository.QueryRepository;
import com.example.MentorS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Cservice {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QueryRepository queryRepository;
    @Autowired
    OrderRepository orderRepository;

    public List<Course> allCourse() {
        List<Course> ls = courseRepository.findAll().stream().toList();
        return ls;
    }

    public Course getCourse(Integer id) {
        return courseRepository.findById(id).get();
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public Orders placeOrder(Orders order) {
        System.out.println("In Place order");
        return orderRepository.save(order);
    }

    public QueryT sendQuery(QueryT queryT) {
        System.out.println("in Query service ");
        return queryRepository.save(queryT);
    }
}
