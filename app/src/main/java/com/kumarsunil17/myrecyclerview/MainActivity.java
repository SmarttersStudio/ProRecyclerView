package com.kumarsunil17.myrecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.smartersstudio.prorecyclerview.ProRecyclerView;
import com.smartersstudio.prorecyclerview.OnMoreListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ProRecyclerView recyclerView;
    List<String> l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.container);
        l = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MainAdapter(l,this));
        recyclerView.addLoaderLayout(R.layout.shimmer_loader);
 //       recyclerView.showLoader();
        //recyclerView.showError();
        recyclerView.showEmpty();
        recyclerView.hideRecycler();

        for (int i=97;  i<100;  i++){
            l.add(String.valueOf((char) i));
        }

        recyclerView.onReload(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Reloading", Toast.LENGTH_SHORT).show();

                recyclerView.hideEmpty();
                recyclerView.showRecycler();
            }
        });
        recyclerView.onRetry(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Retrying", Toast.LENGTH_SHORT).show();

                if (l.size()==0) {
                    recyclerView.showEmpty();
                    recyclerView.hideRecycler();
                }
                recyclerView.hideError();
            }
        });
        recyclerView.setOnMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int overallItemsCount, int maxLastVisiblePosition) {

            }
        });
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                for (int i=100;  i<120;  i++){
                    l.add(String.valueOf((char) i));
                }
                recyclerView.setAdapter(new MainAdapter(l,MainActivity.this));
                recyclerView.setRefreshing(false);
            }
        });

    }
}
