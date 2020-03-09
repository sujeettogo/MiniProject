package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Member;
public class signup extends AppCompatActivity {
    Data data;
    static int r=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        final EditText e1=(EditText)findViewById(R.id.e1);
        final EditText e2=(EditText)findViewById(R.id.e2);
        final EditText e3=(EditText)findViewById(R.id.e3);
        Button bs=(Button)findViewById(R.id.bs);
        data=new Data();
        final DatabaseReference reff= FirebaseDatabase.getInstance().getReference().child("Data");

        bs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e2.getText().toString().equals(e3.getText().toString())) {
                    SharedPreferences sp = getApplicationContext().getSharedPreferences("haha", 0);
                    int r = sp.getInt("B", 0);
                    r++;
                    SharedPreferences.Editor ed = sp.edit();
                    String user = e1.getText().toString();
                    String pass = e2.getText().toString();
                    data.setUsername(user);
                    data.setPassword(pass);
                    ed.putInt("B", r);
                    ed.commit();
                    reff.child(String.valueOf(r)).setValue(data);

                    Intent i1=new Intent(signup.this,MainActivity.class);
                    startActivity(i1);
                }
                else
                {
                    Toast t2=Toast.makeText(getApplicationContext(),"Password does not match",Toast.LENGTH_LONG);
                    t2.show();
                }
            }
        });
    }
}
