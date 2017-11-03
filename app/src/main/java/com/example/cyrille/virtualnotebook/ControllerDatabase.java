package com.example.cyrille.virtualnotebook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cyrille on 29/10/17.
 */

public class ControllerDatabase extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "SqliteListviewDB";
    public static final String TABLE_NAME = "LanguageDetails";
    public ControllerDatabase(Context applicationcontext) {
        super(applicationcontext, DATABASE_NAME, null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table to insert data
        // db.execSQL("DROP TABLE IF EXISTS UserDetails");
        String query;
        query = "CREATE TABLE IF NOT EXISTS LanguageDetails(Id INTEGER PRIMARY KEY AUTOINCREMENT,EnglishWord VARCHAR,FrenchWord VARCHAR,Category VARCHAR);";
        db.execSQL(query);


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query ;
        query = "DROP TABLE IF EXISTS LanguageDetails";
        db.execSQL(query);
        onCreate(db);
    }

    public void deleteSelectedWord(String WordToDelete)
    {
        //Open the database
        SQLiteDatabase db = getWritableDatabase();

        //Execute sql query to remove from database
        //NOTE: When removing by String in SQL, value must be enclosed with ''
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE  EnglishWord = '" + WordToDelete + "'");

        //Close the database
        db.close();
    }
}