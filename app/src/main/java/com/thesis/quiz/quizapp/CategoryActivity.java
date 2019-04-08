package com.thesis.quiz.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.thesis.quiz.quizapp.Adapter.CategoryAdapter;
import com.thesis.quiz.quizapp.Common.SpaceDecoration;
import com.thesis.quiz.quizapp.DBHelper.DBHelper;
import com.thesis.quiz.quizapp.Model.Category;

import java.util.List;


public class CategoryActivity extends AppCompatActivity {
    private List<Category> categoryList;
    RecyclerView recyclerView;
    private LayoutInflater inflater;
    private Animation fadeInAnim;
    private View layoutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater = getLayoutInflater();
        fadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in2);

        layoutView = inflater.inflate(R.layout.activity_category, null, false);
        layoutView.startAnimation(fadeInAnim);
        setContentView(layoutView);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_category);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        try {
            CategoryAdapter adapter = new CategoryAdapter(DBHelper.getInstance(this).getAllCategories(), this);

            int spacePixel = 4;
            recyclerView.addItemDecoration(new SpaceDecoration(spacePixel));
            recyclerView.setAdapter(adapter);
        }
        catch (Exception e)
        {
            Log.d("test", e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CategoryActivity.this, MainActivity.class);
        startActivity(intent);
    }
}