package com.thesis.quiz.quizapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_cat);
        toolbar.setTitle("Select Category");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_close:
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
                break;


        }
        return super.onOptionsItemSelected(item);
    }
}