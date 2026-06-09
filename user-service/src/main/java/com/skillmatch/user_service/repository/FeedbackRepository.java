
// src/main/java/com/skillmatch/user_service/repository/FeedbackRepository.java
package com.skillmatch.user_service.repository;

import com.skillmatch.user_service.model.Feedback;
import com.skillmatch.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> findByProfessional(User professional);
}