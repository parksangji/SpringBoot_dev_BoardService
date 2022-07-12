package com.comdolidoli.devboard.controller;

import com.comdolidoli.devboard.entity.QuestionEntity;
import com.comdolidoli.devboard.repository.QuestionRepository;
import com.comdolidoli.devboard.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService  questionService;

    @RequestMapping("/list")
    public String QuestionList(Model model){
        List<QuestionEntity> questionEntityList = questionService.getList();
        model.addAttribute("questionList",questionEntityList);
        return "question_list";
    }

    @RequestMapping("/list/{id}")
    public String QuestionListId(Model model, @PathVariable("id") Integer id){
        QuestionEntity question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_list_id";
    }
}
