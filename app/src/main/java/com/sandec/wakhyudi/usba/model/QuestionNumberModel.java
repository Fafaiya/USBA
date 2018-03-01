package com.sandec.wakhyudi.usba.model;

/**
 * Created by wakhyudi on 24/02/18.
 */

public class QuestionNumberModel {
    private int questionNumber;
    private int posisiSoal;

    public QuestionNumberModel(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public int getPosisiSoal() {
        return posisiSoal;
    }

    public void setPosisiSoal(int posisiSoal) {
        this.posisiSoal = posisiSoal;
    }
}
