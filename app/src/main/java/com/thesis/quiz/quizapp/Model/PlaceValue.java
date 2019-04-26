package com.thesis.quiz.quizapp.Model;

public class PlaceValue {
    private int id;
    private int num1;
    private int num2;
    private int num3;
    private int answer;

    public PlaceValue(){}

    public PlaceValue(int id, int num1, int num2, int num3, int answer) {
        this.id = id;
        this.num1 = num1;
        this.num2 = num2;
        this.num3 = num3;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNum1() {
        return num1;
    }

    public void setNum1(int num1) {
        this.num1 = num1;
    }

    public int getNum2() {
        return num2;
    }

    public void setNum2(int num2) {
        this.num2 = num2;
    }

    public int getNum3() {
        return num3;
    }

    public void setNum3(int num3) {
        this.num3 = num3;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
