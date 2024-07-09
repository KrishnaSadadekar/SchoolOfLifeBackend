package com.example.MentorS.controller;


import com.example.MentorS.models.*;
import com.example.MentorS.repository.*;
//import com.example.MentorS.service.AdminService;
import com.example.MentorS.service.Cservice;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class HomeController {
    @Autowired
    Cservice cservice;

    @Autowired
    CourseRepository courseRepository;


    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TrainerRepository trainerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    OrderRepository orderRepository;
    private static final String trainerImg = "src/main/resources/img/trainers";
    private static final String courseImg = "src/main/resources/img/courses";
//------------------------USER ACCESS-----------------------------------------------

//    @GetMapping("/addUser")
//    public ResponseEntity<?> addUser() {
//        try {
//            User user=new User();
//            user.setName("krishna");
//            user.setEmail("krushna1994@gmail.com");
//            user.setRole("Role_Admin");
//            user.setPassword(passwordEncoder.encode("123"));
//            userRepository.save(user);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


    @GetMapping("/user/role")
    public String getUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get the current user's roles
        System.out.println("Authentication: " + authentication.isAuthenticated());
        System.out.println("Authentication: " + authentication.getAuthorities().toString());
        return authentication.getAuthorities().toString();
    }

    //-----------------------------------------------------------------------------------
    @PostMapping("/query")
    public ResponseEntity<?> Query(@RequestBody QueryT queryT) {
        try {


            QueryT rs = cservice.sendQuery(queryT);
            System.out.println(rs.getId() + "->>" + rs.getMessage());

            return ResponseEntity.ok(rs.getId());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/all-courses")
    public ResponseEntity<?> allCourses() {

        List<Course> ls = cservice.allCourse();
        return ResponseEntity.ok(ls);
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<?> course(@PathVariable("id") Integer id) {
        System.out.println("->" + id);
        Course rs = cservice.getCourse(id);
        return ResponseEntity.ok(rs);
    }


    @GetMapping("/all-trainers")
    public ResponseEntity<?> allTrainers() {
        System.out.println("In training session");
        List<Trainer> ls = trainerRepository.findAll().stream().toList();
        return ResponseEntity.ok(ls);
    }

    @GetMapping("/all-categories")
    public ResponseEntity<?> allCategories() {
        System.out.println("In training session");
        List<Category> ls = categoryRepository.findAll().stream().toList();
        return ResponseEntity.ok(ls);
    }

    @GetMapping("/dashboard")
    public String welcome() {
        return "WelcomeAdmin";
    }


    //Fetching images for courses and trainer
    @GetMapping("/trainerimage/{imageName}")
    public ResponseEntity<byte[]> trainerGetImage(@PathVariable String imageName) throws IOException {
        System.out.println("In image trainer ! ");
        Path imagePath = Paths.get(trainerImg, imageName);
        if (Files.exists(imagePath)) {
            byte[] imageBytes = Files.readAllBytes(imagePath);
            String contentType = Files.probeContentType(imagePath);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", contentType);

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/courseimage/{imageName}")
    public ResponseEntity<byte[]> courseGetImage(@PathVariable String imageName) throws IOException {
        System.out.println("In image course ! ");
        Path imagePath = Paths.get(courseImg, imageName);
        if (Files.exists(imagePath)) {
            byte[] imageBytes = Files.readAllBytes(imagePath);
            String contentType = Files.probeContentType(imagePath);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", contentType);

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/get_orders")
    public ResponseEntity<?> getAllOrders()
    {
        List<Orders>ls=orderRepository.findAll().stream().toList();
        return ResponseEntity.ok(ls);
    }

}
