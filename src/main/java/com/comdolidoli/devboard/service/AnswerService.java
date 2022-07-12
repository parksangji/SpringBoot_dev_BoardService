package com.comdolidoli.devboard.service;

import com.comdolidoli.devboard.config.DataNotFoundException;
import com.comdolidoli.devboard.entity.AnswerEntity;
import com.comdolidoli.devboard.entity.QuestionEntity;
import com.comdolidoli.devboard.entity.UserEntity;
import com.comdolidoli.devboard.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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

    public AnswerEntity getAnswer(Integer id) {
        Optional<AnswerEntity> answer = this.answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }
    public void modify(AnswerEntity answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }
    public void vote(AnswerEntity answer, UserEntity siteUser) {
        answer.getVoter().add(siteUser);
        this.answerRepository.save(answer);
    }
    public void delete(AnswerEntity answer) {
        this.answerRepository.delete(answer);
    }
}
