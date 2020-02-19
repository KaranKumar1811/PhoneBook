package com.example.phonebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    String first,last,Number,address11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText etName = (EditText)findViewById(R.id.editText);
        final EditText etlastName = (EditText)findViewById(R.id.editText1);
        final EditText etNumber = (EditText)findViewById(R.id.editText3);
        final EditText etAddress = (EditText)findViewById(R.id.editText5);
        Button savebutton = (Button)findViewById(R.id.saveButton);
        Button contact_List = (Button)findViewById(R.id.list);



        contact_List.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(MainActivity.this, Contacts.class);
                startActivity(k);
            }
        });


        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                first = etName.getText().toString();
                last = etlastName.getText().toString();
                Number = etNumber.getText().toString();
                address11 = etAddress.getText().toString();


                Log.i("first name", first);
                if(first.isEmpty()  || last.isEmpty()  || Number.isEmpty()  || address11.isEmpty()){
                    Log.i("heloo", "heloo");
                }
                else{
                    try {
                        SQLiteDatabase myDatabase = openOrCreateDatabase("person3", MODE_PRIVATE, null);

                        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS person3 (firstName VARCHAR,lastName VARCHAR,phoneNumber VARCHAR,address VARCHAR , id INTEGER PRIMARY KEY)");
                        myDatabase.execSQL("INSERT INTO person3 (firstName,lastName ,phoneNumber ,address) VALUES ('" +first+ "','" +last+ "', '" +Number+ "', '" +address11+ "')");

                        Cursor c = myDatabase.rawQuery("SELECT * FROM person3", null);
//
                        int nameIndex = c.getColumnIndex("firstName");
                        int lastIndex = c.getColumnIndex("lastName");
                        int phone = c.getColumnIndex("phoneNumber");
                        int address = c.getColumnIndex("address");
                        int idIndex = c.getColumnIndex("id");

                        c.moveToFirst();
//                    Intent k = new Intent(this, Main2Activity.class);
//                    startActivity(k);
                        Intent k = new Intent(MainActivity.this, Contacts.class);
                        startActivity(k);

                        while (c != null) {
                            Log.i("name", c.getString(nameIndex));
                            Log.i("name", c.getString(lastIndex));
                            Log.i("name", c.getString(phone));
                            Log.i("name", c.getString(address));

                            Log.i("id", c.getString(idIndex));

                            c.moveToNext();

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }}
        });



    }




}