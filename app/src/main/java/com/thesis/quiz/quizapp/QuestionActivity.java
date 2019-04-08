package com.thesis.quiz.quizapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.thesis.quiz.quizapp.Common.Common;
import com.thesis.quiz.quizapp.DBHelper.DBHelper;
import com.thesis.quiz.quizapp.Model.Question;

import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    private int counter, questionIndex, points;
    private final int QUESTION_TIMEOUT = 3000;
//    private Button btnNext;
    private RadioGroup rgQuestions;
    private RadioButton rbAnswerA, rbAnswerB, rbAnswerC, rbAnswerD;
    private TextView tvQuestionCount, tvQuestionText, tvPoints;
    private List<Question> questions;
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        questionIndex = points = counter = 0;
        questions = DBHelper.getInstance(this).getQuestionByCategory(Common.selectedCategory.getCategoryId());

        linearLayout = (LinearLayout)findViewById(R.id.lifePane);
        setLife();

        tvQuestionCount = (TextView)findViewById(R.id.tv_question_count);
        tvQuestionText = (TextView)findViewById(R.id.tv_question_text);
        tvPoints = (TextView)findViewById(R.id.tv_points);

        rgQuestions = (RadioGroup)findViewById(R.id.rgQuestions);
        rbAnswerA = (RadioButton)findViewById(R.id.rbAnswerA);
        rbAnswerB = (RadioButton)findViewById(R.id.rbAnswerB);
        rbAnswerC = (RadioButton)findViewById(R.id.rbAnswerC);
        rbAnswerD = (RadioButton)findViewById(R.id.rbAnswerD);
        initQuestion();
//        btnNext = (Button)findViewById(R.id.btn_next);
//        btnNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goNext();
//            }
//        });

        rgQuestions.setOnCheckedChangeListener(new RGQuestionListener());
    }

    public void initQuestion(){
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

    public void goNext(){
        if(rgQuestions.getCheckedRadioButtonId() == -1){
            return;
        }

        if(questionIndex < questions.size() - 1){
            questionIndex++;
            initQuestion();
            rgQuestions.setOnCheckedChangeListener(null);
            resetOptions();
            rgQuestions.setOnCheckedChangeListener(new RGQuestionListener());
            setClickable(true);
        }
        else{
            
        }
    }

    public void resetOptions(){
        rgQuestions.clearCheck();

        for(int i = 0; i < rgQuestions.getChildCount(); i++){
            RadioButton rb = (RadioButton) rgQuestions.getChildAt(i);
            rb.setCompoundDrawables(null, null, null, null);
            rb.setBackgroundResource(R.drawable.regular_state_radio_button);
        }
    }

    public void setClickable(boolean isSet){
        for(int i = 0; i < rgQuestions.getChildCount(); i++){
            RadioButton rb = (RadioButton) rgQuestions.getChildAt(i);
            rb.setClickable(isSet);
        }
    }

    public void setLife(){
        for(int i = 0; i < Common.life; i++){
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.heart);

            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(lp);

            linearLayout.addView(imageView);
        }
    }

    public void showResult(){

        Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
        startActivity(intent);
    }

    private class RGQuestionListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            View checkedRb = (View) findViewById(checkedId);

            if(checkedRb != null){
                int checkedIndex = rgQuestions.indexOfChild(checkedRb);
                int correctAnswerIndex = ((int) questions.get(questionIndex).getCorrectAnswer().toUpperCase().charAt(0)) - 65;

                Drawable checkImg = getBaseContext().getResources().getDrawable(R.drawable.ic_check_black_14dp);
                checkImg.setBounds(0, 0, 60, 60);

                Drawable clearImg = getBaseContext().getResources().getDrawable(R.drawable.ic_clear_white_14dp);
                clearImg.setBounds(0, 0, 60, 60);

                RadioButton rbCorrect = (RadioButton) rgQuestions.getChildAt(correctAnswerIndex);
                rbCorrect.setBackgroundResource(R.drawable.correct_state_radio_button);

                rbCorrect.setCompoundDrawables(null, null, checkImg, null);

                if (checkedIndex != correctAnswerIndex) {
                    checkedRb.setBackgroundResource(R.drawable.wrong_state_radio_button);
                    ((RadioButton)checkedRb).setCompoundDrawables(null, null, clearImg, null);
                    if(Common.life > 0){
                        final MediaPlayer mp = MediaPlayer.create(QuestionActivity.this, R.raw.wronganswer);
                        mp.start();
                        Common.wrongCount++;
                        Common.life--;
                        linearLayout.removeAllViews();
                        setLife();
                    }

                }
                if (checkedIndex == correctAnswerIndex) {
                    final MediaPlayer mp = MediaPlayer.create(QuestionActivity.this, R.raw.correctanswer);
                    mp.start();
                    Common.points += 10;
                    Common.correctCount++;

                    tvPoints.setText(String.valueOf(Common.points) + " points");
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if((Common.life == 0 || questionIndex == questions.size() - 1)){
                            showResult();
                        }else{
                            goNext();
                        }
                    }
                }, QUESTION_TIMEOUT);
            }

            setClickable(false);
        }
    }

    @Override
    public void onBackPressed() {
        return;
    }
}