package com.project.recipesearch.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.recipesearch.R;
import com.project.recipesearch.models.Results;

import java.util.ArrayList;

public class RecipeSearchAdapter extends RecyclerView.Adapter<RecipeSearchAdapter.ViewHolder> {

    ArrayList<Results> recipeResultsList;
    private final ItemClickListener itemClickListener;

    public RecipeSearchAdapter(ArrayList<Results> recipeResultsList, ItemClickListener itemClickListener) {
        this.recipeResultsList = recipeResultsList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_search_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Results recipeRes = recipeResultsList.get(position);

        Glide.with(holder.itemView.getContext()).load(recipeRes.getThumbnail()).into(holder.recipeImage);
        holder.dishTitle.setText(recipeRes.getTitle().trim());

        if (recipeRes.isFavorite())
            holder.recipeFav.setImageResource(R.drawable.like_filled_heart);
        else
            holder.recipeFav.setImageResource(R.drawable.like_unfilled_heart);

        holder.recipeFav.setOnClickListener(v -> {
            if (recipeRes.isFavorite())
                itemClickListener.likedClicked(position, false);
            else
                itemClickListener.likedClicked(position, true);


        });


    }

    @Override
    public int getItemCount() {
        return recipeResultsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView recipeImage;
        TextView dishTitle;
        ImageView recipeFav;


        ViewHolder(View itemView) {
            super(itemView);
            recipeImage = itemView.findViewById(R.id.recipe_image);
            dishTitle = itemView.findViewById(R.id.recipe_title);
            recipeFav = itemView.findViewById(R.id.liked_heart_iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(recipeResultsList.get(getAdapterPosition()));
        }
    }

    public interface ItemClickListener {
        void onItemClick(Results recipeModel);

        void likedClicked(int position, Boolean isFlag);
    }
}
