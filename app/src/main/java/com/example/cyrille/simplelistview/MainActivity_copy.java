
package com.example.cyrille.simplelistview;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by cyrille on 29/10/17.
 */

public class MainActivity_copy extends AppCompatActivity implements View.OnClickListener{
    ControllerDatabase db =new ControllerDatabase(this);
    SQLiteDatabase database;
    EditText Word_en, Word_fr, Category;
    Button Submitdatabtn,Showdatabtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Word_en = (EditText) findViewById(R.id.etText_eng);

        Word_en.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null&& (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(Word_en.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });
        Word_fr = (EditText) findViewById(R.id.etText_fre);
        Category = (EditText) findViewById(R.id.etCategory);
        Submitdatabtn= (Button) findViewById(R.id.btnSave);
        Showdatabtn=(Button) findViewById(R.id.btnShow);
        Submitdatabtn.setOnClickListener(this);
        Showdatabtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.btnSave)
        {
            database=db.getWritableDatabase();
            database.execSQL("INSERT INTO LanguageDetails(EnglishWord,FrenchWord,Category)VALUES('"+ Word_en.getText()+"','"+ Word_fr.getText()+"','"+ Category.getText()+"')" );
            Toast.makeText(this,"Word saved in the database",Toast.LENGTH_LONG).show();
        }
        else  if(v.getId()==R.id.btnShow)
        {
            Intent intent=new Intent(this,DisplayDataListView_old.class);
            startActivity(intent);

        }
    }
}
