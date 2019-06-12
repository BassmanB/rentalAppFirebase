package com.example.rentalapp;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rentalapp.Common.Common;

import com.example.rentalapp.Model.Object;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ObjectDetail extends AppCompatActivity {


    TextView object_name, object_price, object_description;
    ImageView  object_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;

    String objectId="";
    FirebaseDatabase database;
    DatabaseReference objectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_detail);


        database = FirebaseDatabase.getInstance();
        objectList = database.getReference("Object");


        btnCart = (FloatingActionButton)findViewById(R.id.btnCart);

        object_description = (TextView)findViewById(R.id.object_description);
        object_name = (TextView)findViewById(R.id.object_name);
        object_price = (TextView)findViewById(R.id.object_price);
        object_image = (ImageView)findViewById(R.id.img_object);


        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsedAppbar);


        if(getIntent() !=  null)
            objectId = getIntent().getStringExtra("objectId");
        if(!objectId.isEmpty()){


            if(Common.isConnectedToInternet(getBaseContext()))
                getDetailObject(objectId);
            else{
                Toast.makeText(ObjectDetail.this, "Please check your connection !!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }


    private void getDetailObject(String objectId){
        objectList.child(objectId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object object = dataSnapshot.getValue(Object.class);


                Picasso.with(getBaseContext()).load(object.getImage())
                    .into(object_image);

                collapsingToolbarLayout.setTitle(object.getName());

                object_name.setText(object.getName());
                object_price.setText(object.getPrice());
                object_description.setText(object.getDescription());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
