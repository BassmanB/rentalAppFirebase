package com.example.rentalapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rentalapp.Common.Common;
import com.example.rentalapp.Interface.ItemClickListener;
import com.example.rentalapp.Model.Object;
import com.example.rentalapp.ViewHolder.ObjectViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ObjectList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference objectList;

    String categoryId = "";

    FirebaseRecyclerAdapter<Object, ObjectViewHolder> adapter;
    FirebaseRecyclerAdapter<Object, ObjectViewHolder> searchAdapter;

    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;



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
            if(Common.isConnectedToInternet(getBaseContext()))
                loadListObject(categoryId);

            else{
                Toast.makeText(ObjectList.this, "Please check your connection !!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        materialSearchBar = (MaterialSearchBar)findViewById(R.id.searchBar);
        materialSearchBar.setHint("Search for a product");
        materialSearchBar.setSpeechMode(false);

        loadSuggest();
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<String> suggest = new ArrayList<String>();
                for(String search:suggest)
                {
                    if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if(!enabled)
                    recyclerView.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text);

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });


    }

    private void startSearch(CharSequence text) {
        searchAdapter = new FirebaseRecyclerAdapter<Object, ObjectViewHolder>(
                Object.class,
                R.layout.object_item,
                ObjectViewHolder.class,
                objectList.orderByChild("Name").equalTo(text.toString())
        ) {
            @Override
            protected void populateViewHolder(ObjectViewHolder viewHolder, Object model, int position) {
                viewHolder.object_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.object_image);

                final Object local =  model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent objectDetail =  new Intent(ObjectList.this, ObjectDetail.class);
                        objectDetail.putExtra("ObjectId",searchAdapter.getRef(position).getKey());
                        startActivity(objectDetail);
                    }
                });
            }
        };
        recyclerView.setAdapter(searchAdapter);
    }

    private void loadSuggest() {
        objectList.orderByChild("MenuId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                            Object item = postSnapshot.getValue(Object.class);
                            suggestList.add(item.getName());
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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
                        Intent objectDetail =  new Intent(ObjectList.this, ObjectDetail.class);
                        objectDetail.putExtra("ObjectId",adapter.getRef(position).getKey());
                        startActivity(objectDetail);
                    }
                });
            }
        };


        recyclerView.setAdapter(adapter);
    }


}
