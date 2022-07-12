package com.comdolidoli.devboard.service;

import com.comdolidoli.devboard.entity.AnswerEntity;
import com.comdolidoli.devboard.entity.QuestionEntity;
import com.comdolidoli.devboard.entity.UserEntity;
import com.comdolidoli.devboard.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;


    public void create(QuestionEntity questionEntity, String content, UserEntity userEntity){
        AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setContent(content);
        answerEntity.setQuestion(questionEntity);
        answerEntity.setCreateDate(LocalDateTime.now());
        answerEntity.setAuthor(userEntity);
        answerRepository.save(answerEntity);
    }
}
