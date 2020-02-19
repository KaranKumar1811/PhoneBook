package com.example.phonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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




        contact_List.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {
                Log.d("list ID : ",String.valueOf(id));
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
                alertDialogBuilder.setMessage("Are you sure, You wanted to make decision");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                myDatabase.delete("person3","id = "+ String.valueOf(id),null);
                                adapter.notifyDataSetChanged();
                                finish();
                            }
                        });
                alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return true;
            }
        });
        adapter.notifyDataSetChanged();



    }


    public void loadData(){

//        Intent k = new Intent(this, Contacts.class);
//        startActivity(k);

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
                           Log.d("ID :",String.valueOf(c.getInt(idIndex)));
                           Log.d("Name :",c.getString(fnameIndex));
                           Log.d("Pos :",String.valueOf(position));
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
