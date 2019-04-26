package com.thesis.quiz.quizapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thesis.quiz.quizapp.Common.Common;
import com.thesis.quiz.quizapp.MainActivity;
import com.thesis.quiz.quizapp.Model.Category;
import com.thesis.quiz.quizapp.PlaceValueActivity;
import com.thesis.quiz.quizapp.QuestionActivity;
import com.thesis.quiz.quizapp.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categories;
    private Context context;
    private MediaPlayer mp;

    public CategoryAdapter(List<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_category_item, viewGroup, false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i) {
        if(categories.get(i).getIsDone() == 0)
            categoryViewHolder.layout.setBackgroundResource(R.drawable.card_view_disabled);
        categoryViewHolder.tvCategoryName.setText(categories.get(i).getCategoryText());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        TextView tvCategoryName;
        CardView cardView;
        RelativeLayout layout;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = (TextView)itemView.findViewById(R.id.tv_category_name);
            cardView = (CardView)itemView.findViewById(R.id.card_category);
            layout = (RelativeLayout)itemView.findViewById(R.id.card_view_main);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Common.selectedCategory = categories.get(getAdapterPosition());
                    if(Common.selectedCategory.getIsDone() == 0) {
                        return;
                    }

                    mp = MediaPlayer.create(context, R.raw.tap);
                    mp.start();

                    Intent intent = new Intent(context, QuestionActivity.class);
                    context.startActivity(intent);
                }
            });
        }


    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        mp.release();
        super.onDetachedFromRecyclerView(recyclerView);
    }
}
