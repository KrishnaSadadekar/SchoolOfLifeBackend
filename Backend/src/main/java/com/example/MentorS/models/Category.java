package com.example.MentorS.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Category {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
 private  int cId;

 private  String categoryName;

    public Category(String categoryName) {

        this.categoryName = categoryName;
    }

    public Category() {
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
