package com.example.MentorS.service;

import com.example.MentorS.models.Category;
import com.example.MentorS.models.Course;
import com.example.MentorS.models.Trainer;
import com.example.MentorS.repository.CategoryRepository;
import com.example.MentorS.repository.CourseRepository;
import com.example.MentorS.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


@Service
public class AdminService {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    TrainerRepository trainerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    private static final String UPLOAD_TrainerImage = "src/main/resources/img/trainers/";
    private static final String UPLOAD_CourseImage = "src/main/resources/img/courses/";

    private String generateImageNameWithDateTime(String originalFileName) {
        // Format the current date and time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

        // Append formatted date and time to the original file name
        return now.format(formatter) + "_" + originalFileName;
    }

    public String uploadImage(MultipartFile file, String filePath) {
        try {
            String imageNameWithDateTime = generateImageNameWithDateTime(file.getOriginalFilename());


            System.out.println("Before saveFile ");

            Path path = Paths.get(filePath, imageNameWithDateTime);

            System.out.println("before");
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("after");
            return imageNameWithDateTime;
        } catch (IOException e) {
            System.out.println("Image upload error" + e.getMessage());
            throw new RuntimeException(e);
        }

    }


    public Course addCourse(Course course, MultipartFile file) {
        System.out.println("In Add Course function");

        if (file.isEmpty()) {
            course.setImgUrl("defaulttrainer.png");
            System.out.println("File is Empty!");

        } else {

            String imageNameWithDateTime = uploadImage(file, UPLOAD_CourseImage);
            course.setImgUrl(imageNameWithDateTime);

        }


        return courseRepository.save(course);
    }

    public Course delete(Integer id) {
        Course course = courseRepository.findById(id).get();

        courseRepository.delete(course);
        return course;
    }

    public Course updateCourse(Course course, MultipartFile file) {
        Course oCourse=courseRepository.findById(course.getId()).get();
        System.out.println("Image "+oCourse.getImgUrl());

        if (file.isEmpty()) {
            course.setImgUrl(oCourse.getImgUrl());
            System.out.println("File is Empty!");

        } else
        {

            String imageNameWithDateTime = uploadImage(file, UPLOAD_CourseImage);
            course.setImgUrl(imageNameWithDateTime);

        }
        System.out.println(course.getImgUrl());
        courseRepository.save(course);
        return course;
    }

    public Trainer addTrainer(Trainer trainer, MultipartFile file) {
        System.out.println("In Add Service ");

        if (file.isEmpty()) {
            trainer.setImgUrl("defaulttrainer.png");
            System.out.println("File is Empty!");

        } else {


            String imageNameWithDateTime = uploadImage(file, UPLOAD_TrainerImage);
            trainer.setImgUrl(imageNameWithDateTime);

        }

        return trainerRepository.save(trainer);


    }

    public Category addCategory(String c) {
        Category category=new Category();
        category.setCategoryName(c);
        return categoryRepository.save(category);
    }


    public Trainer deleteTrainer(Integer id)
    {
        Optional<Trainer> trainer=trainerRepository.findById(id);
        trainerRepository.delete(trainer.get());
        return trainer.get();
    }

    public  Category deleteCategory(Integer id)
    {
        Optional<Category> category=categoryRepository.findById(id);
        categoryRepository.delete(category.get());
        return category.get();
    }

}
