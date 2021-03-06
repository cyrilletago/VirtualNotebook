package com.example.cyrille.virtualnotebook;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

public class WordDetail extends AppCompatActivity implements View.OnClickListener, OnItemSelectedListener{
    ControllerDatabase db =new ControllerDatabase(this);
    BottomNavigationView navigation;
    SQLiteDatabase database;
    EditText Word_en, Word_fr, Category;
    Button Submitdatabtn,Showdatabtn;
    String wordGotEn, wordGotFr, categoryGot;
    Spinner mySpinner;

    TextToSpeech t1;
    ImageView b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_detail);
        b1 = (ImageView)findViewById(R.id.pronunciation);

       /* Category = (EditText) findViewById(R.id.etCategory);
        Category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == Category.getId()){
                    Category.setCursorVisible(true);
                }
            }
        });*/

        mySpinner = (Spinner) findViewById(R.id.etCategory);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(WordDetail.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        Word_en = (EditText) findViewById(R.id.etText_eng);

        Word_en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == Word_en.getId()){
                    Word_en.setCursorVisible(true);
                }
            }
        });

        /*Word_en.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null&& (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(Word_en.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });*/
        Word_fr = (EditText) findViewById(R.id.etText_fre);

        Submitdatabtn= (Button) findViewById(R.id.btnSave);
        Showdatabtn=(Button) findViewById(R.id.btnShow);
        Submitdatabtn.setOnClickListener(this);
        Showdatabtn.setOnClickListener(this);

        // Check the status of the selected word. If one was delected, we want to show its details

        if (MainActivity.wordSelected)
        {
            String categoryWord = getIntent().getStringExtra("CATEGORY_TO_PASS");
            int spinnerPosition = myAdapter.getPosition(categoryWord);
            mySpinner.setSelection(spinnerPosition);
            // Category.setText(categoryWord);

            String englishWordClicked = getIntent().getStringExtra("ENGLISH_WORD_TO_PASS");
            Word_en.setText(englishWordClicked);

            String frenchTranslation = getIntent().getStringExtra("FRENCH_WORD_TO_PASS");
            Word_fr.setText(frenchTranslation);

            MainActivity.wordSelected = false;
        }

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status!= TextToSpeech.ERROR)
                {
                    t1.setLanguage(Locale.FRENCH);
                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = Word_fr.getText().toString();
                // Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        initInstances();
    }

    public void onPause(){
        if(t1!=null)
        {
            t1.stop();
            t1.shutdown();
        }
        super.onPause();

    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.btnSave)
        {
            wordGotEn = Word_en.getText().toString().trim();
            wordGotFr = Word_fr.getText().toString().trim();
            // categoryGot = Category.getText().toString().trim();
            categoryGot = mySpinner.getSelectedItem().toString();
            Validators validators = new Validators();

            if (!validators.isAlpha(wordGotEn) || wordGotEn.isEmpty() ||
                            !validators.isAlpha(wordGotFr) || wordGotFr.isEmpty() ||
                                    !validators.isAlpha(categoryGot) || mySpinner.getSelectedItemPosition() == 0)
            {
                Toast.makeText(this,"Invalid Entry: Make sure you select a category",Toast.LENGTH_LONG).show();

            }else if(isAlreadyInDatabase(wordGotEn)){
                database = db.getWritableDatabase();
                database.execSQL("UPDATE LanguageDetails " +
                                    "SET FrenchWord = '" + wordGotFr + "', Category = '" + categoryGot +
                                        "' WHERE EnglishWord = '" + wordGotEn + "' ");
                  Toast.makeText(this, wordGotEn + " has been updated in the database.", Toast.LENGTH_LONG).show();
            }else {
                database = db.getWritableDatabase();
                database.execSQL("INSERT INTO LanguageDetails(EnglishWord,FrenchWord,Category)VALUES('" + wordGotEn + "','" + wordGotFr + "','" + categoryGot + "')");
                Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();

                // Clear the entered text after saving to the database
                Word_en.setText("");
                Word_fr.setText("");
                mySpinner.setSelection(0);
                // Category.setText("");
            }
        }
        else  if(v.getId()==R.id.btnShow)
        {
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);

        }
    }


    public void initInstances()
    {
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelp.disableShiftMode(bottomNavigationView);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.navigation_notebook:
                        //Do some thing here
                        Intent intent = new Intent(WordDetail.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_add:
                        break;
                    case R.id.navigation_test:
                        Intent intent_test = new Intent(WordDetail.this, LanguageQuiz.class);
                        // intent_test.putExtra("PREPARED_WORD_FOR_TEST", MainActivity.indexCheckWordlist.get(0));
                        startActivity(intent_test);
                        break;
                    case R.id.navigation_cathegory:
                        //Do some thing here
                        Intent intent_category = new Intent(WordDetail.this, Category.class);
                        startActivity(intent_category);
                        break;
                }
                return false;
            }
        });

    }

    private boolean isAlreadyInDatabase(String wordToSave) {
           // We will extract our words into this array
            for(int i = 0; i < MainActivity.english_list.size(); i++ ) {
            if (! MainActivity.English_list[i].equals(wordToSave.trim())) {
                } else {
                   return true;
            }
            }
        return false;
    }
}


