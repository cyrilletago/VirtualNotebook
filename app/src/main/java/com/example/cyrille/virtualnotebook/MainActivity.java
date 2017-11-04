
package com.example.cyrille.virtualnotebook;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 *  Created by cyrille on 29/10/17.
 */

public class MainActivity extends AppCompatActivity{
    BottomNavigationView navigation;
    ControllerDatabase controllerDatabase = new ControllerDatabase(this);
    SQLiteDatabase db;
    public ArrayList<String> Id = new ArrayList<String>();
    public ArrayList<String> Word_en = new ArrayList<String>();
    public ArrayList<String> Word_fr = new ArrayList<String>();
    public ArrayList<String> Category = new ArrayList<String>();
    public static ArrayList<String> english_list;
    public static boolean wordSelected = false;
    ListView lv;
    ArrayAdapter<String> newListAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        english_list = new ArrayList<String>();
        lv = (ListView) findViewById(R.id.lstvw);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TextView clickedView = (TextView) view;
                String selectedFromList = (String) lv.getItemAtPosition(position);

                displayClickedWordDetails(selectedFromList);
            }
        });
        registerForContextMenu(lv);
        initInstances();

    }


    @Override
    protected void onResume() {
        displayData();
        super.onResume();
    }



    public void initInstances() {
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.navigation_notebook:
                        break;
                    case R.id.navigation_add:
                        //Do some thing here
                        Intent intent_add = new Intent(MainActivity.this, WordDetail.class);
                        startActivity(intent_add);
                        break;
                    case R.id.navigation_test:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        break;
                    case R.id.navigation_cathegory:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        break;
                }
                return false;
            }
        });

    }


    private void displayData() {
        english_list.clear();
        db = controllerDatabase.getReadableDatabase();
        controllerDatabase.onCreate(db);
        Cursor cursor = db.rawQuery("SELECT * FROM  LanguageDetails ORDER BY EnglishWord ASC",null);
        Id.clear();
        Word_en.clear();
        Word_fr.clear();
        Category.clear();
        if (cursor.moveToFirst()) {
            do {
                // Id.add(cursor.getString(cursor.getColumnIndex("Id")));
                // Word_en.add(cursor.getString(cursor.getColumnIndex("EnglishWord")));
                // Word_fr.add(cursor.getString(cursor.getColumnIndex("FrenchWord")));
                // Category.add(cursor.getString(cursor.getColumnIndex("Category")));
                english_list.add(cursor.getString(cursor.getColumnIndex("EnglishWord")));
            } while (cursor.moveToNext());
        }
        //CustomAdapter ca = new CustomAdapter(this, Id, Word_en, Word_fr, Category);
        //lv.setAdapter(ca);
        //code to set adapter to populate list
        newListAdapter = new ArrayAdapter<String>(this, R.layout.layout, english_list);
        lv.setAdapter(newListAdapter);
        cursor.close();
    }


    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Delete");
    }

    public boolean onContextItemSelected(MenuItem item){
        if(item.getTitle()=="Edit")Toast.makeText(getApplicationContext(), "Edit Clicked", Toast.LENGTH_LONG).show();
        if(item.getTitle()=="Delete")Toast.makeText(getApplicationContext(), "Delete Clicked", Toast.LENGTH_LONG).show();
        return true;
    }

    private void displayClickedWordDetails(String clickedWord) {
        // We will extract our words into this array
        String[] extractedData;
        Cursor new_cursor = db.rawQuery("SELECT * FROM LanguageDetails WHERE EnglishWord = '" + clickedWord + "'", null);
        // Extract data into an array of string
        if (new_cursor.moveToFirst()) {

            String[] columnNames = new_cursor.getColumnNames();
            extractedData = new String[columnNames.length];

            // Use a loop to fill the array
            for (int i = 0; i < columnNames.length; i++) {

                // Assume every column is int

                extractedData[i] = new_cursor.getString(new_cursor.getColumnIndex(columnNames[i]));

                // Notify that a word was selected
                wordSelected = true;

                // Pass the intent to the WordDetails Activity wth the required data
                Intent wordDisplayIntent = new Intent(getBaseContext(), WordDetail.class);
                wordDisplayIntent.putExtra("ENGLISH_WORD_TO_PASS", extractedData[1]);
                wordDisplayIntent.putExtra("FRENCH_WORD_TO_PASS", extractedData[2]);
                wordDisplayIntent.putExtra("CATEGORY_TO_PASS", extractedData[3]);
                startActivity(wordDisplayIntent);
            }

        }

        new_cursor.close();
        // db.close();
    }
    public void deleteSelectedWord(String WordToDelete)
    {
        
    }

}