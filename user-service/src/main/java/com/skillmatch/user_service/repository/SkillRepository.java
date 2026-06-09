
package com.skillmatch.user_service.repository;

import com.skillmatch.user_service.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    // TROVA le skill usando professional.id
    List<Skill> findByProfessionalId(Long professionalId);
}