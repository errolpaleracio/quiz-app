package com.thesis.quiz.quizapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
    private TextView tvQuestionCount, tvQuestionText, tvPoints, tvDirection;
    private List<Question> questions;
    private LinearLayout linearLayout;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_question);
        toolbar.setTitle(Common.selectedCategory.getCategoryText());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
        //rbAnswerD = (RadioButton)findViewById(R.id.rbAnswerD);
        initQuestion();
//        btnNext = (Button)findViewById(R.id.btn_next);
//        btnNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goNext();
//            }
//        });

        rgQuestions.setOnCheckedChangeListener(new RGQuestionListener());

        tvDirection = (TextView)findViewById(R.id.tv_directions);
        tvDirection.setText("Directions: " + Common.selectedCategory.getDirection());
    }

    public void initQuestion(){
        if(questions.size() > 0 && questionIndex < questions.size()){
            Question question = questions.get(questionIndex);
            SpannableString str = new SpannableString(question.getQuestionText());
            int underlineIndex = question.getUnderlineIndex();

            if(Common.selectedCategory.getCategoryId() == 1){
                str.setSpan(new UnderlineSpan(), underlineIndex, underlineIndex + 1, 0);
            }

            tvQuestionCount.setText(String.format("%d/%d question", questionIndex + 1, questions.size()));
            tvQuestionText.setText(str);
            rbAnswerA.setText(question.getAnswerA());
            rbAnswerB.setText(question.getAnswerB());
            if(Common.selectedCategory.getCategoryId() != 2 && Common.selectedCategory.getCategoryId() != 6) {
                rbAnswerC.setText(question.getAnswerC());
                rbAnswerC.setVisibility(View.VISIBLE);
            }else
                rbAnswerC.setVisibility(View.GONE);
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
            mp.release();
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
                        mp = MediaPlayer.create(QuestionActivity.this, R.raw.wronganswer);
                        mp.start();
                        Common.wrongCount++;
                        Common.life--;
                        linearLayout.removeAllViews();
                        setLife();
                    }

                }
                if (checkedIndex == correctAnswerIndex) {
                    mp = MediaPlayer.create(QuestionActivity.this, R.raw.yehey);
                    mp.start();
                    Common.points += 1;
                    Common.correctCount++;

                    tvPoints.setText(String.valueOf(Common.points) + " points");
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if((questionIndex == questions.size() - 1 || Common.life == 0)){
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_close) {
            if(id == R.id.action_close){
                new AlertDialog.Builder(this)
                        .setTitle("Exit Application")
                        .setMessage("Are you sure you want to close the game?")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finishAffinity();
                            }
                        })
                        .show();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}