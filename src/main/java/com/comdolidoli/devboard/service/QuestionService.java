package com.comdolidoli.devboard.service;

import com.comdolidoli.devboard.entity.QuestionEntity;
import com.comdolidoli.devboard.repository.QuestionRepository;
import com.comdolidoli.devboard.config.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public Page<QuestionEntity> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.questionRepository.findAll(pageable);
    }

    public void createQuestion(String subject, String content) {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setSubject(subject);
        questionEntity.setContent(content);
        questionEntity.setCreateDate(LocalDateTime.now());
        questionRepository.save(questionEntity);
    }
}
