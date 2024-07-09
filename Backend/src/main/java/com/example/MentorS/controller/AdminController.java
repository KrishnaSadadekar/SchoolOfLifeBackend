package com.example.MentorS.controller;

import com.example.MentorS.models.Category;
import com.example.MentorS.models.Course;
import com.example.MentorS.models.Orders;
import com.example.MentorS.models.Trainer;
import com.example.MentorS.repository.CategoryRepository;
import com.example.MentorS.repository.CourseRepository;
import com.example.MentorS.repository.OrderRepository;
import com.example.MentorS.repository.TrainerRepository;
import com.example.MentorS.service.AdminService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {
    @Autowired
    AdminService adminService;

    @Autowired
    TrainerRepository trainerRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    OrderRepository orderRepository;


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProcess(@RequestParam("id") Integer id,
                                           @RequestParam("course") String courseJson ,@RequestParam("file") MultipartFile file
            , @RequestParam("category") String categoryName, @RequestParam("trainer") String trainerName){
        Optional<Course> c=courseRepository.findById(id);
        System.out.println(c.get().getName());
        System.out.println(categoryName+" ->"+trainerName);
        if(categoryName.isEmpty())
        {
            System.out.println("In--");
            categoryName="Data";
        }
        if(trainerName.isEmpty())
        {
            System.out.println("in 2");
            trainerName="Krishna";

        }
        System.out.println(categoryName+"  ->>>>>>>>>>>>>>"+trainerName);
        Category category=categoryRepository.getCategoryByCategoryName(categoryName);
        Trainer trainer=trainerRepository.getTrainerByName(trainerName);

        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("In Update");
        try {
            Course course = objectMapper.readValue(courseJson, Course.class);
            course.setId(id);

            course.setTrainer(trainer);
            course.setCategory(category);
            Course rs=adminService.updateCourse(course,file);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable("id") Integer id) {
        System.out.println("Id:->" + id);
        try {
            Course rs =adminService.delete(id) ;
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PostMapping("/add_course")
    public ResponseEntity<HttpStatus> addProcess(@RequestParam("course") String courseJson, @RequestParam("file") MultipartFile file, @RequestParam("category") String categoryName, @RequestParam("trainer") String trainerName) {
        System.out.println("In add course  Mapping ");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Course course = objectMapper.readValue(courseJson, Course.class);
            course.setCategory(categoryRepository.getCategoryByCategoryName(categoryName));
            course.setTrainer(trainerRepository.getTrainerByName(trainerName));
            System.out.println(course.getDescription() + "-" + course.getName() + "-" + course.getTrainer() + "-" + course.getCategory().getCategoryName() + "->" + course.getPrice() + "->" + course.getSeats());

            System.out.println("In Add Course");
            Course rs = adminService.addCourse(course, file);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (JsonProcessingException e) {
            System.out.println("Json Error:-" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            System.out.println("->" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/add_trainer")
    public ResponseEntity<HttpStatus> addTrainer(@RequestParam("trainer") String trainerJson, @RequestParam("file") MultipartFile file) {
        System.out.println("In add Trainer");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Trainer trainer = objectMapper.readValue(trainerJson, Trainer.class);
            System.out.println("Trainer Name:->" + trainer.getName() + "-" + trainer.getLastName());

            System.out.println(">>>" + file.getOriginalFilename());


            Trainer rs = adminService.addTrainer(trainer, file);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (JsonProcessingException e) {
            System.out.println("JSON error");
            throw new RuntimeException(e);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add_category")
    public ResponseEntity<HttpStatus> addCategory(@RequestBody String category) {
        try {

            Category rs = adminService.addCategory(category);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } 
    }




}
