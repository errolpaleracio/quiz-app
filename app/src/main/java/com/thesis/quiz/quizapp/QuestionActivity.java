package com.thesis.quiz.quizapp;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TimeUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.thesis.quiz.quizapp.Common.Common;
import com.thesis.quiz.quizapp.DBHelper.DBHelper;
import com.thesis.quiz.quizapp.Model.Question;

import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    private boolean hasTimeOut;
    private int counter, questionIndex, points;
    private Button btnNext;
    private RadioGroup rgQuestions;
    private RadioButton rbAnswerA, rbAnswerB, rbAnswerC, rbAnswerD;
    private TextView tvQuestionCount, tvQuestionText, tvPoints;
    private List<Question> questions;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(Common.selectedCategory.getCategoryText() + " Quiz");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuestionActivity.this, MainActivity.class));
                finish();
            }
        });

        hasTimeOut = false;

        questionIndex = points = counter = 0;
        questions = DBHelper.getInstance(this).getQuestionByCategory(Common.selectedCategory.getCategoryId());

        tvQuestionCount = (TextView)findViewById(R.id.tv_question_count);
        tvQuestionText = (TextView)findViewById(R.id.tv_question_text);
        tvPoints = (TextView)findViewById(R.id.tv_points);

        rgQuestions = (RadioGroup)findViewById(R.id.rgQuestions);
        rbAnswerA = (RadioButton)findViewById(R.id.rbAnswerA);
        rbAnswerB = (RadioButton)findViewById(R.id.rbAnswerB);
        rbAnswerC = (RadioButton)findViewById(R.id.rbAnswerC);
        rbAnswerD = (RadioButton)findViewById(R.id.rbAnswerD);
        initQuestion();
        btnNext = (Button)findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goNext();
            }
        });

    }

    private void initQuestion(){
        if(questions.size() > 0 && questionIndex < questions.size()){
            Question question = questions.get(questionIndex);
            tvQuestionCount.setText(String.format("%d/%d question", questionIndex + 1, questions.size()));
            tvQuestionText.setText(question.getQuestionText());
            rbAnswerA.setText(question.getAnswerA());
            rbAnswerB.setText(question.getAnswerB());
            rbAnswerC.setText(question.getAnswerC());
            rbAnswerD.setText(question.getAnswerD());
        }
    }

    private void goNext(){
        if(rgQuestions.getCheckedRadioButtonId() == -1) {
            Toast.makeText(QuestionActivity.this, "Please select an answer.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(questionIndex == questions.size() - 1) {
            btnNext.setText("Finish");
        }

        View checkedRb = (View)findViewById(rgQuestions.getCheckedRadioButtonId());
        int index = rgQuestions.indexOfChild(checkedRb);
        int correctAnswer = ((int)questions.get(questionIndex).getCorrectAnswer().toUpperCase().charAt(0)) - 65;

        if(index == correctAnswer) {
            points += 10;
            tvPoints.setText(String.valueOf(points) + " points");
        }

        questionIndex++;
        initQuestion();
        rgQuestions.clearCheck();
    }
}
