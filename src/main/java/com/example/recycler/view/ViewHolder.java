package com.example.recycler.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycler.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView textView;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        //laver itemView til et linearLayout
        LinearLayout linearLayout = (LinearLayout) itemView;
        textView = linearLayout.findViewById(R.id.textView);

    }

    public void setData(String data) {

        textView.setText(data);
    }
}
