package com.comdolidoli.devboard.controller;

import com.comdolidoli.devboard.entity.QuestionEntity;
import com.comdolidoli.devboard.form.AnswerForm;
import com.comdolidoli.devboard.form.QuestionForm;
import com.comdolidoli.devboard.repository.QuestionRepository;
import com.comdolidoli.devboard.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService  questionService;

    @RequestMapping("/list")
    public String QuestionList(Model model,@RequestParam(value="page", defaultValue="0") int page) {
        Page<QuestionEntity> paging = this.questionService.getList(page);
        model.addAttribute("paging", paging);
        return "question_list";
    }

    @RequestMapping("/list/{id}")
    public String QuestionListId(Model model, @PathVariable("id") Integer id,AnswerForm answerForm){
        QuestionEntity question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_list_id";
    }

    @GetMapping("/create")
    public String CreateQuestion(QuestionForm questionForm){
        return "question_form";
    }

    @PostMapping("/create")
    public String CreateQuestion(@Valid QuestionForm questionForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "question_form";
        }
        this.questionService.createQuestion(questionForm.getSubject(),questionForm.getContent());
        return "redirect:/question/list";
    }
}
