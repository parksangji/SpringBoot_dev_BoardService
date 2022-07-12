package com.comdolidoli.devboard.controller;

import com.comdolidoli.devboard.entity.QuestionEntity;
import com.comdolidoli.devboard.form.AnswerForm;
import com.comdolidoli.devboard.service.AnswerService;
import com.comdolidoli.devboard.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id,
                               @Valid AnswerForm answerForm, BindingResult bindingResult){
        QuestionEntity questionEntity = questionService.getQuestion(id);
        if(bindingResult.hasErrors()){
            model.addAttribute("question", questionEntity);
            return "question_list_id";
        }
        this.answerService.create(questionEntity,answerForm.getContent());
        return String.format("redirect:/question/list/%s",id);
    }
}
