package com.example.cyrille.virtualnotebook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cyrille on 29/10/17.
 */

public class ControllerDatabase extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME="SqliteListviewDB";
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
}