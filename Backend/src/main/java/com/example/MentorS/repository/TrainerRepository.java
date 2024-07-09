package com.example.MentorS.repository;

import com.example.MentorS.models.Trainer;
import com.example.MentorS.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

public interface TrainerRepository extends JpaRepository<Trainer,Integer> {

    @Query("select u from Trainer u where u.name=:name")
    Trainer getTrainerByName(@Param("name") String name);

}
