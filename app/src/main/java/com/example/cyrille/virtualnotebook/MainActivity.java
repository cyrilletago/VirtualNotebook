
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * Created by cyrille on 29/10/17.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    BottomNavigationView navigation;
    ControllerDatabase controllerDatabase = new ControllerDatabase(this);
    SQLiteDatabase db;
    public static LinkedHashMap<String, Integer> mapIndex;

    public ArrayList<String> Id = new ArrayList<String>();
    public ArrayList<String> Word_en = new ArrayList<String>();
    public ArrayList<String> Word_fr = new ArrayList<String>();
    public ArrayList<String> Category = new ArrayList<String>();

    ListView lv;
    ArrayAdapter<String> newListAdapter;

    // Visible everywhere in the app
    public static ArrayList<String> english_list;
    public static ArrayList<String> french_list;
    public static boolean wordSelected = false;
    public static String clickedWord = null;
    public static String[] English_list;
    public static List<Integer> indexCheckWordlist;
    public static int testRound;
    public static int testScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        english_list = new ArrayList<String>();
        french_list = new ArrayList<String>();
        testRound = 0;
        testScore = 0;
        lv = (ListView) findViewById(R.id.lstvw);

        //getListView().setFastScrollEnabled(true);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //TextView clickedView = (TextView) view;
                String selectedFromList = (String) lv.getItemAtPosition(position);
                clickedWord = selectedFromList;

                displayClickedWordDetails(selectedFromList);
            }
        });
        registerForContextMenu(lv);
        initInstances();


        // ==============UPDATE WORD FOR THE TEST=================
        indexCheckWordlist = new ArrayList<>();



    }

    @Override
    protected void onResume() {
        displayData();

        testRound = 0;
        getIndexList(English_list);
        displayIndex();
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
                        break;
                    case R.id.navigation_add:
                        Intent intent_add = new Intent(MainActivity.this, WordDetail.class);
                        startActivity(intent_add);
                        break;
                    case R.id.navigation_test:
                        Intent intent_test = new Intent(MainActivity.this, LanguageQuiz.class);
                        startActivity(intent_test);
                        break;
                    case R.id.navigation_cathegory:
                        Intent intent_category = new Intent(MainActivity.this, Category.class);
                        startActivity(intent_category);
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
                english_list.add(cursor.getString(cursor.getColumnIndex("EnglishWord")));
                french_list.add(cursor.getString(cursor.getColumnIndex("FrenchWord")));
            } while (cursor.moveToNext());
        }
        //code to set adapter to populate list
         newListAdapter = new ArrayAdapter<String>(this, R.layout.layout, english_list);
         lv.setAdapter(newListAdapter);
        cursor.close();


        //Update the english list and reset clicked word
        if(!english_list.isEmpty()) {
            English_list = new String[english_list.size()];
            English_list = english_list.toArray(English_list);
        }
        clickedWord = null;
        for (int i=0; i<english_list.size(); i++) {
            indexCheckWordlist.add(i);
        }
        //and here is the point
        Collections.shuffle(indexCheckWordlist);
        //============================================================
    }


    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Delete");
        if (v.getId() == R.id.lstvw){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            clickedWord = English_list[info.position];
            menu.setHeaderTitle(String.format("%s Options", clickedWord));
        }
    }

    public boolean onContextItemSelected(final MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if(item.getTitle()=="Edit"){
            // Toast.makeText(getApplicationContext(), "Edit Clicked", Toast.LENGTH_LONG).show();
            displayClickedWordDetails(clickedWord);
        }

        // Case Delete is selected
        if(item.getTitle()=="Delete"){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setCancelable(false);
            alertDialog.setMessage(String.format("Delete item %s?",clickedWord));
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    controllerDatabase.deleteSelectedWord(clickedWord);

                    //Update your ArrayList
                    displayData();
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    clickedWord = null;
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
         db.close();
    }

    private void getIndexList(String[] English_list) {
        mapIndex = new LinkedHashMap< String, Integer>();
        for (int j = 0; j < English_list.length; j++) {
            String Eng_list = English_list[j];
            String index = Eng_list.substring(0, 1);

            if (mapIndex.get(index) == null)
                mapIndex.put(index, j);
        }
    }

    private void displayIndex() {
        LinearLayout indexLayout = (LinearLayout) findViewById(R.id.side_index);

        TextView textView;
        List<String> indexList = new ArrayList<String>(mapIndex.keySet());
        for (String index : indexList) {
            textView = (TextView) getLayoutInflater().inflate(R.layout.side_view, null);
            textView.setText(index);
            textView.setOnClickListener(this);
            indexLayout.addView(textView);
        }
    }

    public void onClick(View view) {
        TextView selectedIndex = (TextView) view;
        lv.setSelection(mapIndex.get(selectedIndex.getText()));
    }

}