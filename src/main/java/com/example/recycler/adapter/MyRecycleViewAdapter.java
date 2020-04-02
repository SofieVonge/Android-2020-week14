package com.example.recycler.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycler.R;
import com.example.recycler.storage.NoteStorage;
import com.example.recycler.view.ViewHolder;



public class MyRecycleViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private NoteStorage storage;

    public MyRecycleViewAdapter(NoteStorage storage) {
        this.storage = storage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the layout we made
        LinearLayout ll = (LinearLayout) LayoutInflater
                            .from(parent.getContext())
                            .inflate(R.layout.customrow, parent, false);

        return new ViewHolder(ll);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //bind data to one row in the view
        holder.setData(storage.list.get(position));
    }

    @Override
    //elementer i listen
    public int getItemCount() {
        return NoteStorage.list.size();
    }
}
