package com.thesis.quiz.quizapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {


    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder questionViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder{

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
