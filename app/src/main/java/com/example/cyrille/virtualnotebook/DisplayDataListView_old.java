package com.example.cyrille.virtualnotebook;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import java.util.ArrayList;

/**
 * Created by cyrille on 29/10/17.
 */

public class DisplayDataListView_old extends AppCompatActivity {
    ControllerDatabase controllerDatabase = new ControllerDatabase(this);
    SQLiteDatabase db;
    private ArrayList<String> Id = new ArrayList<String>();
    private ArrayList<String> Word_en = new ArrayList<String>();
    private ArrayList<String> Word_fr = new ArrayList<String>();
    private ArrayList<String> Category = new ArrayList<String>();
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdata_listview);
        lv = (ListView) findViewById(R.id.lstvw);
    }
    @Override
    protected void onResume() {
        displayData();
        super.onResume();
    }
    private void displayData() {
        db = controllerDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM  LanguageDetails ORDER BY EnglishWord ASC",null);
        Id.clear();
        Word_en.clear();
        Word_fr.clear();
        Category.clear();
        if (cursor.moveToFirst()) {
            do {
                Id.add(cursor.getString(cursor.getColumnIndex("Id")));
                Word_en.add(cursor.getString(cursor.getColumnIndex("EnglishWord")));
                Word_fr.add(cursor.getString(cursor.getColumnIndex("FrenchWord")));
                Category.add(cursor.getString(cursor.getColumnIndex("Category")));
            } while (cursor.moveToNext());
        }
        CustomAdapter ca = new CustomAdapter(DisplayDataListView_old.this,Id, Word_en, Word_fr, Category);
        lv.setAdapter(ca);
        //code to set adapter to populate list
        cursor.close();
    }
}