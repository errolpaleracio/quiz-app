package com.thesis.quiz.quizapp.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.thesis.quiz.quizapp.Model.Category;
import com.thesis.quiz.quizapp.Model.PlaceValue;
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
                int id = cursor.getInt(0);
                String name = cursor.getString(1);;
                String direction = cursor.getString(2);
                int isDone = cursor.getInt(3);

                Category category = new Category(id, name, direction, isDone);
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
                int categoryId = cursor.getInt(6);

                String questionText = cursor.getString(1);
                String answerA = cursor.getString(2);
                String answerB = cursor.getString(3);
                String answerC = cursor.getString(4);
                String correctAnswer = cursor.getString(5);
                int underlineIndex = cursor.getInt(7);
                String answerD = cursor.getString(8);

                Question question = new Question(id, categoryId, questionText,  answerA, answerB, answerC, correctAnswer, underlineIndex, answerD);
                questions.add(question);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return questions;
    }

    public List<PlaceValue> getPlaceValue(){
        SQLiteDatabase db = instance.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM PlaceValue ORDER BY RANDOM();", null);
        List<PlaceValue> placeValues = new ArrayList<>();
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){

                int id = cursor.getInt(0);
                int num1 = cursor.getInt(1);
                int num2 = cursor.getInt(2);
                int num3 = cursor.getInt(3);
                int answer = cursor.getInt(4);

                PlaceValue placeValue = new PlaceValue(id, num1, num2, num3, answer);
                placeValues.add(placeValue);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return placeValues;
    }

    public void update(long id, int isDone){
        SQLiteDatabase db = instance.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("isDone", isDone);
        db.update("Category", values, "id=" + id, null);
    }

}
