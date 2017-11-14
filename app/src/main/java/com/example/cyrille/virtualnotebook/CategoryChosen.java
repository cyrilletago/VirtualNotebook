
package com.example.cyrille.virtualnotebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * Created by cyrille on 11/11/17.
 */

public class CategoryChosen extends AppCompatActivity{
    BottomNavigationView navigation;
    ControllerDatabase controllerDatabase = new ControllerDatabase(this);
    SQLiteDatabase db;
    public ArrayList<String> Id = new ArrayList<String>();
    public ArrayList<String> Word_en = new ArrayList<String>();
    public ArrayList<String> Word_fr = new ArrayList<String>();
    public ArrayList<String> Category = new ArrayList<String>();
    public static ArrayList<String> categorisedEnglishList;

    ListView lv;
    ArrayAdapter<String> newListAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categorisedEnglishList = new ArrayList<String>();

        lv = (ListView) findViewById(R.id.lstvw);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // TextView clickedView = (TextView) view;
                String selectedFromList = (String) lv.getItemAtPosition(position);
                MainActivity.clickedWord = selectedFromList;

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
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelp.disableShiftMode(bottomNavigationView);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.navigation_notebook:
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_add:
                        Intent intent_add = new Intent(CategoryChosen.this, WordDetail.class);
                        startActivity(intent_add);
                        break;
                    case R.id.navigation_test:
                        Intent intent_test = new Intent(CategoryChosen.this, LanguageQuiz.class);
                        startActivity(intent_test);
                        break;
                    case R.id.navigation_cathegory:
                        Intent intent_category = new Intent(CategoryChosen.this, Category.class);
                        startActivity(intent_category);
                        break;
                }
                return false;
            }
        });

    }


    private void displayData() {
        categorisedEnglishList.clear();
        db = controllerDatabase.getReadableDatabase();
        controllerDatabase.onCreate(db);
        String categoryClicked = getIntent().getStringExtra("CATEGORY_TO_BE_OPENED");
        Cursor cursor = db.rawQuery("SELECT * FROM  LanguageDetails WHERE Category = '" + categoryClicked + "' ORDER BY EnglishWord ASC",null);
        Id.clear();
        Word_en.clear();
        Word_fr.clear();
        Category.clear();
        if (cursor.moveToFirst()) {
            do {
                categorisedEnglishList.add(cursor.getString(cursor.getColumnIndex("EnglishWord")));
            } while (cursor.moveToNext());
        }
        //code to set adapter to populate list
         newListAdapter = new ArrayAdapter<String>(this, R.layout.layout, categorisedEnglishList);
         lv.setAdapter(newListAdapter);
        cursor.close();


        //Update the english list and reset clicked word
        if(!MainActivity.english_list.isEmpty()) {
            MainActivity.English_list = new String[MainActivity.english_list.size()];
            MainActivity.English_list = MainActivity.english_list.toArray(MainActivity.English_list);
        }
        MainActivity.clickedWord = null;
        //===============================================================================

    }


    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Delete");
        if (v.getId() == R.id.lstvw){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            MainActivity.clickedWord = MainActivity.English_list[info.position];
            menu.setHeaderTitle(String.format("%s Options", MainActivity.clickedWord));
        }
    }

    public boolean onContextItemSelected(final MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if(item.getTitle()=="Edit"){
            displayClickedWordDetails(MainActivity.clickedWord);
        }

        // Case Delete is selected
        if(item.getTitle()=="Delete"){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setCancelable(false);
            alertDialog.setMessage(String.format("Delete item %s?",MainActivity.clickedWord));
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    controllerDatabase.deleteSelectedWord(MainActivity.clickedWord);

                    //Update your ArrayList
                    displayData();

                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.clickedWord = null;
                    dialog.cancel();
                }
            });
            alertDialog.show();

        }

        return true;
    }

    public void displayClickedWordDetails(String clickedWord) {
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
                MainActivity.wordSelected = true;

                // Pass the intent to the WordDetails Activity wth the required data
                Intent wordDisplayIntent = new Intent(getBaseContext(), WordDetail.class);
                wordDisplayIntent.putExtra("ENGLISH_WORD_TO_PASS", extractedData[1]);
                wordDisplayIntent.putExtra("FRENCH_WORD_TO_PASS", extractedData[2]);
                wordDisplayIntent.putExtra("CATEGORY_TO_PASS", extractedData[3]);
                startActivity(wordDisplayIntent);
            }

        }

        new_cursor.close();
         db.close();
    }


}