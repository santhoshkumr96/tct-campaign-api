package com.tct.tctcampaign.controller;

import com.tct.tctcampaign.repo.QuestionRepository;
import com.tct.tctcampaign.service.QuestionService;
import com.tct.tctcampaign.model.response.QuestionTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class QuestionareController {

    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionRepository questionRepository;

    @GetMapping("/v1/getquestionlist")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public List<QuestionTO> getQuestionList(@RequestParam String search){
        return questionService.fetchAll(search);
    };

    @GetMapping("/v1/getquestionlistApproved")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public List<QuestionTO> getQuestionListApproved(@RequestParam String search){
        return questionService.fetchAllApproved(search);
    };


    @PostMapping("/v1/getquestion")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public QuestionTO getQuestion(@RequestBody QuestionTO questionId){
        return questionService.fetchQuestion(questionId.getQuestionId());
    };


    @PostMapping("/v1/createquestion")
    @PreAuthorize("hasRole('USER')")
    public QuestionTO createQuestion(@RequestBody QuestionTO question)  {
        return questionService.create(question);
    };

    @PostMapping("/v1/editquestion")
    @PreAuthorize("hasRole('USER')")
    public QuestionTO editQuestion(@RequestBody QuestionTO question)  {
        return questionService.editQuestion(question);
    };

    @PostMapping("/v1/deleteQuestion")
    @PreAuthorize("hasRole('USER')")
    public void deleteQuestion(@RequestBody QuestionTO questionId) throws NoSuchFieldException {
        questionService.delete(
              questionId.getQuestionId()
        );
    };


    @PostMapping("/v1/update-question-status")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateQuestionStatus(@RequestBody QuestionTO question){
        questionService.updateQuestionStatus(question);
    }

    @GetMapping("/v1/searchQuestion")
    public Object searchQuestion(@RequestParam String search){
        return questionRepository.searchByQuestionName(search);
    }

    @GetMapping("/v1/searchApprovedQuestion")
    public Object searchApprovedQuestion(@RequestParam String search){
        return questionRepository.searchApprovedQuestionByNameByQuestionName(search);
    }
}
