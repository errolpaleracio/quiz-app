package com.thesis.quiz.quizapp.Common;

import com.thesis.quiz.quizapp.Model.Category;
import com.thesis.quiz.quizapp.Model.Question;

import java.util.ArrayList;
import java.util.List;

public class Common {
    public static Category selectedCategory = new Category();
    public static List<Category> categoryList = new ArrayList<>();
    public static List<Question> questionList = new ArrayList<>();
    public static int life = 5, points = 0, correctCount = 0, wrongCount = 0;
}
