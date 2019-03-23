package com.thesis.quiz.quizapp.Model;

public class Category {
    private int categoryId;
    private String categoryText;

    public Category() {
    }

    public Category(int categoryId, String categoryText) {
        this.categoryId = categoryId;
        this.categoryText = categoryText;
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
}
