package com.example.cyrille.virtualnotebook;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WordDetail extends AppCompatActivity implements View.OnClickListener{
    ControllerDatabase db =new ControllerDatabase(this);
    BottomNavigationView navigation;
    SQLiteDatabase database;
    EditText Word_en, Word_fr, Category;
    Button Submitdatabtn,Showdatabtn;
    String wordGotEn, wordGotFr, categoryGot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_detail);

        Category = (EditText) findViewById(R.id.etCategory);
        Category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == Category.getId()){
                    Category.setCursorVisible(true);
                }
            }
        });
        Word_en = (EditText) findViewById(R.id.etText_eng);

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
            Category.setText(categoryWord);

            String englishWordClicked = getIntent().getStringExtra("ENGLISH_WORD_TO_PASS");
            Word_en.setText(englishWordClicked);

            String frenchTranslation = getIntent().getStringExtra("FRENCH_WORD_TO_PASS");
            Word_fr.setText(frenchTranslation);

            MainActivity.wordSelected = false;
        }

        initInstances();
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.btnSave)
        {
            wordGotEn = Word_en.getText().toString().trim();
            wordGotFr = Word_fr.getText().toString().trim();
            categoryGot = Category.getText().toString().trim();

            if (!isAlpha(wordGotEn) || wordGotEn.isEmpty() ||
                            !isAlpha(wordGotFr) || wordGotFr.isEmpty() ||
                                    !isAlpha(categoryGot) || categoryGot.isEmpty())
            {
                Toast.makeText(this,"Invalid Entry",Toast.LENGTH_LONG).show();

            }else if(isAlreadyInDatabase(wordGotEn)){
                  Toast.makeText(this, wordGotEn + " already in the database. Edit it.", Toast.LENGTH_LONG).show();
            }else {
                database = db.getWritableDatabase();
                database.execSQL("INSERT INTO LanguageDetails(EnglishWord,FrenchWord,Category)VALUES('" + Word_en.getText() + "','" + Word_fr.getText() + "','" + Category.getText() + "')");
                Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();

                // Clear the entered text after saving to the database
                Word_en.setText("");
                Word_fr.setText("");
                Category.setText("");
            }
        }
        else  if(v.getId()==R.id.btnShow)
        {
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);

        }
    }

    public boolean isAlpha(String s){
        String pattern= "^[a-zA-Z]*$";
        return s.matches(pattern);
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
                        //Do some thing here
                        // Intent intent_add = new Intent(WordDetail.this, WordDetail.class);
                        // startActivity(intent_add);
                        break;
                    case R.id.navigation_test:
                        //Do some thing here
                        Intent intent_test = new Intent(WordDetail.this, Test.class);
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


