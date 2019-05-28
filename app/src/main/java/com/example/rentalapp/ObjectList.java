package com.example.rentalapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rentalapp.Interface.ItemClickListener;
import com.example.rentalapp.Model.Object;
import com.example.rentalapp.ViewHolder.ObjectViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ObjectList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference objectList;

    String categoryId = "";

    FirebaseRecyclerAdapter<Object, ObjectViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        database = FirebaseDatabase.getInstance();
        objectList = database.getReference("Object");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if (getIntent() != null)
            categoryId = getIntent().getStringExtra("CategoryID");
        if( categoryId != null && !categoryId.isEmpty()){
            loadListObject(categoryId);
        }
    }

    private void loadListObject(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Object, ObjectViewHolder>(Object.class, R.layout.object_item, ObjectViewHolder.class,objectList.orderByChild("MenuId").equalTo(categoryId)) {
            @Override
            protected void populateViewHolder(ObjectViewHolder viewHolder, Object model, int position) {
                viewHolder.object_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.object_image);


                final Object local =  model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(ObjectList.this, "", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };


        recyclerView.setAdapter(adapter);
    }


}
