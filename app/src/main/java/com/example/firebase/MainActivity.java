package com.example.firebase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;

    private DatabaseReference myRef;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        myRef=mFirebaseDatabase.getReference();


        //Toast t= Toast.makeText(getApplicationContext(),"Firebase Connected",Toast.LENGTH_SHORT);
        //t.show();
        final DatabaseReference reff= FirebaseDatabase.getInstance().getReference().child("Data");
        Button b1=(Button)findViewById(R.id.b1);
        Button b2=(Button)findViewById(R.id.b2);
        final EditText e1=(EditText)findViewById(R.id.e1);
        final EditText e2=(EditText)findViewById(R.id.e2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(MainActivity.this,signup.class);
                startActivity(i1);
                finish();

            }
        });
        Set<String> set=new HashSet<String>();
        set.add("0");
        set.add("0");
        SharedPreferences sharedPref = getSharedPreferences("application", Context.MODE_PRIVATE);
        final Set<String> login=sharedPref.getStringSet("Login",null);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=1;
                for(i=1;i<=5;i++) {
                    final DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Data").child(String.valueOf(i));
                    reff.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Data d = dataSnapshot.getValue(Data.class);
                            //e1.setText(d.getUsername());
                            //e2.setText(d.getPassword());
                            if (e1.getText().toString().equals(d.getUsername()) && e2.getText().toString().equals(d.getPassword())) {
                                Toast tr = Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_LONG);
                                tr.show();

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });

    }

}
