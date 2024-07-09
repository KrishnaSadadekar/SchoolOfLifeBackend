package com.example.MentorS.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String lastName;

    private String email;

    private String city;

    private String contactNumber;
    @ManyToOne(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    @JsonBackReference
    private Course course;
    @Transient
    private int courseFees;

    private String status;

    private String razorId;


    public Orders(String name, String lastName, String email, String city, String contactNumber, Course course, int courseFees, String status, String razorId) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.city = city;
        this.contactNumber = contactNumber;
        this.course = course;
        this.courseFees = courseFees;
        this.status = status;
        this.razorId = razorId;
    }

    public Orders() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRazorId() {
        return razorId;
    }

    public void setRazorId(String razorId) {
        this.razorId = razorId;
    }

    public int getCourseFees() {
        return courseFees;
    }

    public void setCourseFees(int courseFees) {
        this.courseFees = courseFees;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
