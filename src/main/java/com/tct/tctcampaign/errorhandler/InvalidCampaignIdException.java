package com.tct.tctcampaign.errorhandler;

public class InvalidCampaignIdException extends Exception {
    public InvalidCampaignIdException(String errorMessage) {
        super(errorMessage);
    }
}