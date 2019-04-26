package com.thesis.quiz.quizapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thesis.quiz.quizapp.Common.Common;
import com.thesis.quiz.quizapp.DBHelper.DBHelper;
import com.thesis.quiz.quizapp.Model.Question;

import org.w3c.dom.Text;

import java.util.List;

public class ResultActivity extends AppCompatActivity {
    private TextView tvScoreResult, tvCorrectResult, tvWrongResult, tvGreetings;
    private Button btnPlayAgain;
    private LinearLayout starPane;
    private int stars;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        starPane = (LinearLayout)findViewById(R.id.star_pane);
        stars = 0;

        if(Common.correctCount >= 16)
            stars = 3;
        else if(Common.correctCount >= 10)
            stars = 2;
        else if(Common.correctCount >= 4)
            stars = 1;

        tvScoreResult = (TextView)findViewById(R.id.tv_score_result);
        tvScoreResult.setText("Score: " + Common.points);

        tvCorrectResult = (TextView)findViewById(R.id.tv_correct_result);
        tvCorrectResult.setText(Common.correctCount + "");

        List<Question> questionList = DBHelper.getInstance(ResultActivity.this).getQuestionByCategory(Common.selectedCategory.getCategoryId());
        int wrongCount = (questionList.size() - Common.correctCount) +   1;

        tvWrongResult = (TextView)findViewById(R.id.tv_wrong_result);
        tvWrongResult.setText(Common.wrongCount + "");

        String greetings = "";
        switch (stars){
            case 0:
                greetings = "Better luck next time.";
                break;
            case 1:
                greetings = "Good!";
                break;
            case 2:
                greetings = "Very good!";
                break;
            case 3:
                greetings = "Excellent!";
                break;
        }

        tvGreetings = (TextView)findViewById(R.id.tv_greetings);
        tvGreetings.setText(greetings);

        btnPlayAgain = (Button) findViewById(R.id.btn_play_again);
        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp = MediaPlayer.create(ResultActivity.this, R.raw.tap);
                mp.start();
                Intent intent = new Intent(ResultActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });
        if(Common.selectedCategory.getCategoryId() < 7 && stars > 0) {
            DBHelper.getInstance(this).update((Common.selectedCategory.getCategoryId()) + 1, 1);
        }

        Common.life = 5;
        Common.points = 0;
        Common.correctCount = 0;
        Common.wrongCount = 0;
        Common.selectedCategory = null;
        showStars();
    }

    private void showStars(){
        for(int i = 0; i < stars; i++){
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.mipmap.rsz_star);

            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(lp);

            starPane.addView(imageView);
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mp.release();
    }
}
