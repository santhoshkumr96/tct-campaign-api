package com.tct.tctcampaign.model.request;

public class QuestionCategoryCreationRequest {

    private String questionCategory;

    private String user;

    public String getQuestionCategory() {
        return questionCategory;
    }

    public void setQuestionCategory(String questionCategory) {
        this.questionCategory = questionCategory;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
