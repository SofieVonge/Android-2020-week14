package com.example.recycler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;

import com.example.recycler.adapter.MyRecycleViewAdapter;
import com.example.recycler.storage.NoteStorage;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyRecycleViewAdapter adapter;
    private boolean isLastItemReached;
    private boolean isScrolling;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NoteStorage.init(this);

        recyclerView = findViewById(R.id.recylerView);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        //we need to make our own layout (customview) and therefore our own adapter in order to handle recyclerviews
        adapter = new MyRecycleViewAdapter(new NoteStorage());
        recyclerView.setAdapter(adapter);
        setUpScrollListener();
    }

    private void setUpScrollListener(){
        RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true; //to prevent multiple calls to Firebase
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItemPosition = manager.findFirstVisibleItemPosition();
                int visibleItemCount = manager.getChildCount();
                int totalItemCount = manager.getItemCount();

                if (isScrolling && (firstVisibleItemPosition + visibleItemCount == totalItemCount)
                    && !isLastItemReached) {

                    isScrolling = false; //to prevent extra calls to Firebase
                    NoteStorage.getNextNotes(); //load next 5 items from Firebase, after the last one
                }

            }
        };
        recyclerView.addOnScrollListener(listener);
    }

    public void notifyAdapter() {
        adapter.notifyDataSetChanged();
    }

    public void setIsLastItemReached(boolean b) {
        isLastItemReached = b;
    }

    public void signOut(View view) {
        finish();
    }
}
