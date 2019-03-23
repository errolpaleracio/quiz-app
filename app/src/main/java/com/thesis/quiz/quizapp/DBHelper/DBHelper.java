package com.thesis.quiz.quizapp.DBHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.thesis.quiz.quizapp.Model.Category;
import com.thesis.quiz.quizapp.Model.Question;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteAssetHelper{


    private static final int DB_VERSION = 1;

    private static DBHelper instance;

    public static synchronized DBHelper getInstance(Context context){
        if(instance == null) {
            instance = new DBHelper(context);
        }
        return instance;
    }

    public DBHelper(Context context) {
        super(context, "test.db", null, DB_VERSION);
    }

    public List<Category> getAllCategories(){
        List<Category> categories = new ArrayList<>();
        SQLiteDatabase db = instance.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Category", null);

        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                int id = cursor.getInt(cursor.getColumnIndex("ID"));
                String name = cursor.getString(cursor.getColumnIndex("Name"));;

                Category category = new Category(id, name);
                categories.add(category);
                cursor.moveToNext();
            }
        }
        cursor.close();

        return categories;
    }

    public List<Question> getQuestionByCategory(int category){
        SQLiteDatabase db = instance.getWritableDatabase();

        Cursor cursor = db.rawQuery(String.format("SELECT * FROM Question WHERE CategoryId=%d ORDER BY RANDOM() LIMIT 30;", category), null);
        List<Question> questions = new ArrayList<>();
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){

                int id = cursor.getInt(0);
                int categoryId = cursor.getInt(7);

                String questionText = cursor.getString(1);
                String answerA = cursor.getString(2);
                String answerB = cursor.getString(3);
                String answerC = cursor.getString(4);
                String answerD = cursor.getString(5);
                String correctAnswer = cursor.getString(6);

                Question question = new Question(id, categoryId, questionText,  answerA, answerB, answerC, answerD, correctAnswer);
                questions.add(question);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return questions;
    }


}