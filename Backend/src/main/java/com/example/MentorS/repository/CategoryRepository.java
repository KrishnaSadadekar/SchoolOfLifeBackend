package com.example.MentorS.repository;

import com.example.MentorS.models.Category;
import com.example.MentorS.models.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    @Query("select u from Category u where u.categoryName=:categoryName")
    Category getCategoryByCategoryName(@Param("categoryName") String categoryName);
}
