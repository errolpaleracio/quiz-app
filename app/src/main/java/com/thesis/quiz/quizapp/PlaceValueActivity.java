package com.thesis.quiz.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.thesis.quiz.quizapp.DBHelper.DBHelper;
import com.thesis.quiz.quizapp.Model.PlaceValue;

import java.util.List;

public class PlaceValueActivity extends AppCompatActivity {

    private List<PlaceValue> placeValues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_value);

        placeValues = DBHelper.getInstance(this).getPlaceValue();


    }

    public void selectAnswer(View view){

    }
}
