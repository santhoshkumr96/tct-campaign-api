package com.tct.tctcampaign.service;

import com.tct.tctcampaign.model.db.Question;
import com.tct.tctcampaign.model.db.Response;
import com.tct.tctcampaign.model.response.QuestionTO;
import com.tct.tctcampaign.repo.ResponseRepository;
import com.tct.tctcampaign.repo.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    ResponseRepository responseRepository;

    @Autowired
    private Validator validator;

    public List<QuestionTO> fetchAll(String search){
        List<QuestionTO> questionList= new ArrayList<>();
        if(search.isEmpty()){
            for(Question questionData :questionRepository.findAll() ){
                QuestionTO questionTO = questionData.toView();
                questionTO.setResponse(responseRepository.findByQuestionId(questionData.getQuestionId()));
                questionList.add(questionTO);
            }
            return questionList;
        }

        for(Question questionData :questionRepository.searchByQuestionNameWhole(search) ){
            QuestionTO questionTO = questionData.toView();
            questionTO.setResponse(responseRepository.findByQuestionId(questionData.getQuestionId()));
            questionList.add(questionTO);
        }

        return questionList;
    }

    public List<QuestionTO> fetchAllApproved(String search){
        List<QuestionTO> questionList= new ArrayList<>();
        if(search.isEmpty()){
            for(Question questionData :questionRepository.findAllApproved() ){
                QuestionTO questionTO = questionData.toView();
                questionTO.setResponse(responseRepository.findByQuestionId(questionData.getQuestionId()));
                questionList.add(questionTO);
            }
            return questionList;
        }

        for(Question questionData :questionRepository.searchByQuestionNameWhole(search) ){
            QuestionTO questionTO = questionData.toView();
            questionTO.setResponse(responseRepository.findByQuestionId(questionData.getQuestionId()));
            questionList.add(questionTO);
        }

        return questionList;
    }

    public QuestionTO fetchQuestion(Integer questionId){

        Question questionData = questionRepository.findByQuestionId(questionId);
            QuestionTO questionTO = questionData.toView();
            questionTO.setResponse(responseRepository.findByQuestionId(questionData.getQuestionId()));

        return questionTO;
    }



    public QuestionTO editQuestion (QuestionTO questionTo)  {
        responseRepository.deleteByQuestionId(questionTo.getQuestionId(),getUserNameFromContext());
        Question questionData = questionRepository.findByQuestionId(questionTo.getQuestionId());
        Set<ConstraintViolation<QuestionTO>> validations = validator.validate(questionTo);
        if(validations.size() > 0)
            throw new ValidationException("please check inputs");
        questionData.setQuestionName(questionTo.getQuestionName());
        questionData.setQuestionDesc(questionTo.getQuestionDesc());
        questionData.setChangedDate(new Date());
        questionData.setResponseType(questionTo.getResponseType());
        questionData.setStatusDesc("PENDING");
        Question savedQuestion = questionRepository.save(questionData);
        responseRepository.deleteByQuestionId(savedQuestion.getQuestionId(),getUserNameFromContext());
        for(Response response: questionTo.getResponse()){
            Set<ConstraintViolation<Response>> validationsForResponse = validator.validate(response);
            if(validations.size() > 0)
                throw new ValidationException("please check inputs");
            Response responseToStore = new Response(response.getResponseName(),
                    response.getResponseDesc(),
                    savedQuestion.getQuestionId(),
                    getUserNameFromContext(),
                    new Date());
            responseToStore.setChangedDate(new Date());
            responseToStore.setResponseId(response.getResponseId());
            responseToStore.setChangedBy(getUserNameFromContext());
            responseToStore.setStatusDesc("PENDING");
            responseRepository.save(responseToStore,getUserNameFromContext());
        }
        getUserNameFromContext();
        return questionTo;
    }

    public QuestionTO create (QuestionTO questionTo)  {
        Set<ConstraintViolation<QuestionTO>> validations = validator.validate(questionTo);
        if(validations.size() > 0)
            throw new ValidationException("please check inputs");
            Question question = new Question(questionTo.getQuestionName(),
                                    questionTo.getQuestionDesc(),
                                    getUserNameFromContext(),
                                    new Date(),
                                    true,
                                    questionTo.getResponseType()

        );
        question.setChangedBy(getUserNameFromContext());
        question.setChangedDate(new Date());
        Question savedQuestion = questionRepository.insert(question);
        for(Response response: questionTo.getResponse()){
            Set<ConstraintViolation<Response>> validationsForResponse = validator.validate(response);
            if(validations.size() > 0)
                throw new ValidationException("please check inputs");
                    Response responseToStore = new Response(response.getResponseName(),
                    response.getResponseDesc(),
                    savedQuestion.getQuestionId(),
                    getUserNameFromContext(),
                    new Date());
                    responseToStore.setQuestionId(savedQuestion.getQuestionId());
                    responseToStore.setChangedDate(new Date());
                    responseToStore.setChangedBy(getUserNameFromContext());
                    responseToStore.setStatusDesc("PENDING");
                    responseRepository.insert(responseToStore);
        }
        getUserNameFromContext();
        return questionTo;
    }

    private String getUserNameFromContext(){

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();

        return username;
    }


    public void delete(Integer questionId) {
        questionRepository.deleteByQuestionId(questionId , getUserNameFromContext());
        responseRepository.deleteByQuestionId(questionId , getUserNameFromContext());
    }

    public void updateQuestionStatus(QuestionTO questionTO) {
        Question question = questionRepository.findByQuestionId(questionTO.getQuestionId());
        question.setStatusDesc(questionTO.getStatusDesc());
        question.setComments(questionTO.getComments());
        if(questionTO.getStatusDesc() == "APPROVED"){
            question.setApprovedBy(getUserNameFromContext());
            question.setApprovedDate(new Date());
        }
        question.setChangedBy(getUserNameFromContext());
        question.setChangedDate(new Date());
        questionRepository.save(question);
    }
}
