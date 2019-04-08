package com.thesis.quiz.quizapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.thesis.quiz.quizapp.Common.Common;
import com.thesis.quiz.quizapp.DBHelper.DBHelper;
import com.thesis.quiz.quizapp.Model.Question;

import java.util.List;

public class ResultActivity extends AppCompatActivity {
    private TextView tvScoreResult, tvCorrectResult, tvWrongResult;
    private Button btnPlayAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvScoreResult = (TextView)findViewById(R.id.tv_score_result);
        tvScoreResult.setText("Score: " + Common.points);

        tvCorrectResult = (TextView)findViewById(R.id.tv_correct_result);
        tvCorrectResult.setText("Correct: " + Common.correctCount);

        List<Question> questionList = DBHelper.getInstance(ResultActivity.this).getQuestionByCategory(Common.selectedCategory.getCategoryId());
        int wrongCount = (questionList.size() - Common.correctCount) +   1;

        tvWrongResult = (TextView)findViewById(R.id.tv_wrong_result);
        tvWrongResult.setText("Wrong: " + Common.wrongCount);

        btnPlayAgain = (Button) findViewById(R.id.btn_play_again);
        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaPlayer mp = MediaPlayer.create(ResultActivity.this, R.raw.tap);
                mp.start();
                Intent intent = new Intent(ResultActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });

        Common.life = 5;
        Common.points = 0;
        Common.correctCount = 0;
        Common.selectedCategory = null;


    }

    @Override
    public void onBackPressed() {
        return;
    }
}
