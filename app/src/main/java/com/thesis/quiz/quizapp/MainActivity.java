package com.thesis.quiz.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.thesis.quiz.quizapp.Adapter.CategoryAdapter;
import com.thesis.quiz.quizapp.Common.SpaceDecoration;
import com.thesis.quiz.quizapp.DBHelper.DBHelper;
import com.thesis.quiz.quizapp.Model.Category;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Category> categoryList;
    Toolbar toolbar;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Math Quiz");
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_category);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

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
}
