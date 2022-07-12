package com.comdolidoli.devboard.repository;

import com.comdolidoli.devboard.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<QuestionEntity,Integer> {
    QuestionEntity findBySubject(String subject);
    QuestionEntity findBySubjectAndContent(String subject,String content);
}
