package com.comdolidoli.devboard.controller;

import com.comdolidoli.devboard.entity.QuestionEntity;
import com.comdolidoli.devboard.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
    private final QuestionService questionService;

    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam String content){
        QuestionEntity questionEntity = questionService.getQuestion(id);

        return String.format("redirect:/question/list/%s",id);
    }
}
