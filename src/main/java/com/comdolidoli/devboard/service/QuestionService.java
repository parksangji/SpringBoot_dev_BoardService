package com.comdolidoli.devboard.service;

import com.comdolidoli.devboard.entity.AnswerEntity;
import com.comdolidoli.devboard.entity.QuestionEntity;
import com.comdolidoli.devboard.entity.UserEntity;
import com.comdolidoli.devboard.repository.QuestionRepository;
import com.comdolidoli.devboard.config.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
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

    private Specification<QuestionEntity> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<QuestionEntity> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                Join<QuestionEntity, UserEntity> u1 = q.join("author", JoinType.LEFT);
                Join<QuestionEntity, AnswerEntity> a = q.join("answerEntityList", JoinType.LEFT);
                Join<AnswerEntity, UserEntity> u2 = a.join("author", JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용
                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자
                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용
                        cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자
            }
        };
    }
    public QuestionEntity getQuestion(Integer id){
        Optional<QuestionEntity> questionEntity = this.questionRepository.findById(id);
        if(questionEntity.isPresent()){
            return questionEntity.get();
        }else{
            throw new DataNotFoundException("question not found");
        }
    }
    public Page<QuestionEntity> getList(int page,String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<QuestionEntity> spec = search(kw);
        return this.questionRepository.findAll(spec,pageable);
    }

    public void createQuestion(String subject, String content, UserEntity userEntity) {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setSubject(subject);
        questionEntity.setContent(content);
        questionEntity.setCreateDate(LocalDateTime.now());
        questionEntity.setAuthor(userEntity);
        questionRepository.save(questionEntity);
    }

    public void modify(QuestionEntity question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }

    public void vote(QuestionEntity question, UserEntity siteUser) {
        question.getVoter().add(siteUser);
        this.questionRepository.save(question);
    }
    public void delete(QuestionEntity question) {
        this.questionRepository.delete(question);
    }
}
