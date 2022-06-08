package com.example.task.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(Context context) {
        super(context, parameter.DB_NAME, null, parameter.DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String create = "CREATE TABLE " + parameter.TABLE_NAME + "("  + parameter.KEY_NAME + " TEXT " + ")";

                                  //+ paramater.KEY_ID + " INTEGER PRIMARY KEY, "

        Log.d("dbFruits", "Query being run is: " + create);

        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if EXISTS "+ parameter.DB_NAME);
    }


//insert data in dataBase
    public void insertuserdata(String str) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Values = new ContentValues();

        Values.put(parameter.KEY_NAME,str);


        db.insert(parameter.TABLE_NAME, null, Values);

        Log.d("dpfruit", "Successfully inserted ");

        db.close();
    }

    //retrieve data from dataBase
    public List<String> getAllDetails() {
        List<String> name = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        //Generate a the query to read from dataBase
        String select = "SELECT * FROM " + parameter.TABLE_NAME;
        Cursor cursor = db.rawQuery(select, null);

        //Loop through now

        if(cursor.moveToFirst()){
            do{
                name.add(cursor.getString(0));

            }while(cursor.moveToNext());
        }
          return  name;
         }



}
