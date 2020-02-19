package com.example.phonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
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


    public void loaddata(){
        ContactHolder holder;

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


        try{

            int fnameIndex = c.getColumnIndex("firstName");
            int lastNameIndex = c.getColumnIndex("lastName");
            int phone = c.getColumnIndex("phoneNumber");
            int address = c.getColumnIndex("address");
            int idIndex = c.getColumnIndex("id");

                    c.moveToFirst();

                    while (c!=null && c.getCount()>0 ){
                        Log.d("ID :",String.valueOf(c.getInt(idIndex)));
                        Log.d("Name :",c.getString(fnameIndex));
                        Log.d("Pos :",String.valueOf(position));


                       if(position==c.getInt(idIndex)-1) {
                           holder.firstname.setText(c.getString(fnameIndex));
                           holder.lastname.setText(c.getString(lastNameIndex));
                           holder.address.setText(c.getString(address));
                           holder.phoneNo.setText(c.getString(phone));
                       }

                        c.moveToNext();
                    }
            }
            catch (Exception e){
                e.printStackTrace();
            }


                    return convertView;

        }

    }

    class ContactHolder{
        TextView firstname,lastname,phoneNo,address;
    }
}
