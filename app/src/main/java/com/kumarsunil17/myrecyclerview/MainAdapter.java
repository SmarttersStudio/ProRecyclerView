package com.kumarsunil17.myrecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {
    List<String> l ;
    Context c;

    public MainAdapter(List<String> l, Context c) {
        this.l = l;
        this.c = c;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(c).inflate(R.layout.demo_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.setTitle(l.get(position));
    }

    @Override
    public int getItemCount() {
        return l.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
