package com.kumarsunil17.myrecyclerview;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainViewHolder extends RecyclerView.ViewHolder {
    TextView title;

    public MainViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.card_title);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }
}
