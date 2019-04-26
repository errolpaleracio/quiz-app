package com.thesis.quiz.quizapp.Model;

public class Category {
    private int categoryId;
    private int isDone;
    private String categoryText;
    private String direction;

    public Category() {
    }

    public Category(int categoryId, String categoryText, String direction, int isDone) {
        this.categoryId = categoryId;
        this.categoryText = categoryText;
        this.direction = direction;
        this.isDone = isDone;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryText() {
        return categoryText;
    }

    public void setCategoryText(String categoryText) {
        this.categoryText = categoryText;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getIsDone() {
        return isDone;
    }

    public void setIsDone(int isDone) {
        this.isDone = isDone;
    }
}
