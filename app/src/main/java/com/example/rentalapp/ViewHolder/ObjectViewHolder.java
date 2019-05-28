package com.example.rentalapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rentalapp.Interface.ItemClickListener;
import com.example.rentalapp.R;

public class ObjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView object_name;
    public ImageView object_image;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public ObjectViewHolder(View itemView) {
        super(itemView);

        object_name = (TextView)itemView.findViewById(R.id.object_name);
        object_image = (ImageView)itemView.findViewById(R.id.object_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(), false);
    }
}
