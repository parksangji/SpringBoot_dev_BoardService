package com.comdolidoli.devboard.service;

import com.comdolidoli.devboard.entity.QuestionEntity;
import com.comdolidoli.devboard.repository.QuestionRepository;
import com.comdolidoli.devboard.setting.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<QuestionEntity> getList(){
        return this.questionRepository.findAll();
    }

    public QuestionEntity getQuestion(Integer id){
        Optional<QuestionEntity> questionEntity = this.questionRepository.findById(id);
        if(questionEntity.isPresent()){
            return questionEntity.get();
        }else{
            throw new DataNotFoundException("question not found");
        }
    }
}
