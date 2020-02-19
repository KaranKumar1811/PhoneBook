package com.example.phonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

public class Contacts extends AppCompatActivity {

    SQLiteDatabase myDatabase ;
    Cursor c ;
    Adapter adapter =new Adapter(Contacts.this,myDatabase,c);
    ListView contact_List;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
       myDatabase = openOrCreateDatabase("person3", MODE_PRIVATE, null);
       c = myDatabase.rawQuery("SELECT * FROM person3", null);
        searchView = findViewById(R.id.searchView);
        contact_List = findViewById(R.id.contact_list);
        contact_List.setAdapter(adapter);

    }


    class Adapter extends BaseAdapter
    {
        Activity activity;



        SQLiteDatabase mDB;
        Cursor cursor ;

        public Adapter(Activity activity, SQLiteDatabase mDB, Cursor cursor) {
            this.activity = activity;
            this.mDB = mDB;
            this.cursor = cursor;
        }



        @Override
        public int getCount() {
            return c.getCount();
        }

        @Override
        public Object getItem(int position) {
            return c.getPosition();
        }

        @Override
        public long getItemId(int position) {
            return c.getPosition();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ContactHolder holder=null;
            if(convertView==null) {
                holder = new ContactHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_layout, parent, false);

                holder.firstname = convertView.findViewById(R.id.first_name);
                holder.lastname = convertView.findViewById(R.id.last_name);
                holder.phoneNo = convertView.findViewById(R.id.contact);
                holder.address = convertView.findViewById(R.id.address);

                convertView.setTag(holder);

            }
            else
            {
                holder = (ContactHolder) convertView.getTag();
            }
            holder.firstname.setId(position);
            holder.lastname.setId(position);
            holder.phoneNo.setId(position);
            holder.address.setId(position);

            return convertView;

        }
    }

    class ContactHolder{
        TextView firstname,lastname,phoneNo,address;
    }
}
