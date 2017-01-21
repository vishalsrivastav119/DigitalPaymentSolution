package com.example.dpouch;

/**
 * Created by 586924 -AJitesh on 10/23/2016 .
 */

public class CardDetailsBean {

    private String cardType;
    private String cardNumber;
    private String tokenId;
    private int cardImg;
    private int amount;
    private String timestmp;
    public String getTimestmp() {
        return timestmp;
    }

    public void setTimestmp(String timestmp) {
        this.timestmp = timestmp;
    }



    public String getCardNumber() {
        return cardNumber;
    }

    public int getCardImg() {
        return cardImg;
    }

    public void setCardImg(int cardImg) {
        this.cardImg = cardImg;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
