package com.example.dovebook.contact.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class dbManager extends SQLiteOpenHelper{

    private Context mContext;

    public dbManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    public static final String CREATE_CONTACTLIST="create table Friend ("
            +"id integer primary key autoincrement, "
            +"userId text, "
            +"userName text, "
            +"userAvatarPath text, "
            +"friendId text)";


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_CONTACTLIST);
        Toast.makeText(mContext,"Create Succeeded",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
