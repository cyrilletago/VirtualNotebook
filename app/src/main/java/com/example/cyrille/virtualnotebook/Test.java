package com.example.cyrille.virtualnotebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;



/**
 * Created by dominiquekogue on 11/3/17.
 */


public class Test extends AppCompatActivity implements View.OnClickListener {
    private Random randomGenerator = new Random();
    CheckBox checkbox,checkbox2,checkbox3;
    Button button_done, button_quit;
    TextView score_text, word_to_check, score;
    BottomNavigationView navigation;
    public ArrayList Temporal_English_list;
    public ArrayList Temporal_French_list;
    public static int indexFromIntent;




    ControllerDatabase db =new ControllerDatabase(this);
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        MainActivity.testRound ++; // increment the testRound
         Log.i("TEST_ROUND", ""+MainActivity.testRound);
        button_done= (Button) findViewById(R.id.btnDone);
        button_quit=(Button) findViewById(R.id.btnQuit);
        button_done.setOnClickListener(this);
        button_quit.setOnClickListener(this);

        word_to_check = (TextView) findViewById(R.id.word_to_check);
        score_text = (TextView) findViewById(R.id.score_text);
        score = (TextView) findViewById(R.id.score);

        indexFromIntent = MainActivity.indexCheckWordlist.get(MainActivity.testRound-1);
        String englishItem = MainActivity.english_list.get(indexFromIntent);
        word_to_check.setText(englishItem);
        score.setText(MainActivity.testScore +"/5");

        Temporal_French_list = new ArrayList<String>();
        Temporal_English_list = new ArrayList<String>();

        checkbox = (CheckBox) findViewById(R.id.choice1);
        checkbox2 = (CheckBox) findViewById(R.id.choice2);
        checkbox3 = (CheckBox) findViewById(R.id.choice3);
        // Populate the temporary lists
        if (!MainActivity.english_list.isEmpty()) {
            Temporal_English_list.addAll(MainActivity.english_list);
            Temporal_French_list.addAll(MainActivity.french_list);
        }

        setCheckboxes(indexFromIntent);

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
              {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                    {
                        // check if value is answer
                        if (MainActivity.testRound < 5)
                        {
                            if (checkbox.getText().equals(MainActivity.french_list.get(indexFromIntent)))
                            {
                                MainActivity.testScore ++;
                                Intent intent_test1 = new Intent(getBaseContext(), Test.class);
                                startActivity(intent_test1);
                            } else
                                {
                                    Intent intent_test1 = new Intent(getBaseContext(), Test.class);
                                    startActivity(intent_test1);
                                }
                        } else { //Post the test results
                            if (checkbox.getText().equals(MainActivity.french_list.get(indexFromIntent)))
                            {MainActivity.testScore ++;}
                            handlePopUp();
                             }
                    }
              }

        );

        checkbox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                {
                    // check if value is answer
                    if (MainActivity.testRound < 5)
                    {
                        if (checkbox2.getText().equals(MainActivity.french_list.get(indexFromIntent)))
                        {
                            MainActivity.testScore ++;
                            Intent intent_test1 = new Intent(getBaseContext(), Test.class);
                            startActivity(intent_test1);
                        } else
                        {
                            Intent intent_test1 = new Intent(getBaseContext(), Test.class);
                            startActivity(intent_test1);
                        }
                    } else { //Post the test results
                        if (checkbox2.getText().equals(MainActivity.french_list.get(indexFromIntent)))
                        {MainActivity.testScore ++;}
                        handlePopUp();
                    }
                }
            }

        );

        checkbox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
             {
                 @Override
                 public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                 {
                     // check if value is answer
                     if (MainActivity.testRound < 5)
                     {
                         if (checkbox3.getText().equals(MainActivity.french_list.get(indexFromIntent)))
                         {
                             MainActivity.testScore ++;
                             Intent intent_test1 = new Intent(getBaseContext(), Test.class);
                             startActivity(intent_test1);
                         } else
                         {
                             Intent intent_test1 = new Intent(getBaseContext(), Test.class);
                             startActivity(intent_test1);
                         }
                     } else { //Post the test results
                         if (checkbox3.getText().equals(MainActivity.french_list.get(indexFromIntent)))
                         {MainActivity.testScore ++;}
                         handlePopUp();
                     }
                 }
             }

        );

        initInstances();
    }



    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.btnDone)
        {
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);
        }
        else  if(v.getId()==R.id.btnQuit)
        {
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);
        }
    }



    public void initInstances()
    {
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelp.disableShiftMode(bottomNavigationView);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.navigation_notebook:
                        //Do some thing here
                        Intent intent = new Intent(Test.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_add:
                        //Do some thing here
                        Intent intent_add = new Intent(Test.this, WordDetail.class);
                        startActivity(intent_add);
                        break;
                    case R.id.navigation_test:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        break;
                    case R.id.navigation_cathegory:
                        //Do some thing here
                        Intent intent_test = new Intent(Test.this, Category.class);
                        startActivity(intent_test);
                        break;
                }
                return false;
            }
        });

    }

    public void setCheckboxes( int tempIndex) {
        if (MainActivity.english_list.size() > 3) {

            int i = randomGenerator.nextInt(3);
            if (i == 0) {
                checkbox.setText((String) Temporal_French_list.get(tempIndex));
                Temporal_French_list.remove(tempIndex);
                Collections.shuffle(Temporal_French_list);
                checkbox2.setText((String) Temporal_French_list.get(0));
                checkbox3.setText((String) Temporal_French_list.get(1));
            } else if (i == 1) {
                checkbox2.setText((String) Temporal_French_list.get(tempIndex));
                Temporal_French_list.remove(tempIndex);
                Collections.shuffle(Temporal_French_list);
                checkbox.setText((String) Temporal_French_list.get(0));
                checkbox3.setText((String) Temporal_French_list.get(1));
            } else if (i == 2) {
                checkbox3.setText((String) Temporal_French_list.get(tempIndex));
                Temporal_French_list.remove(tempIndex);
                Collections.shuffle(Temporal_French_list);
                checkbox.setText((String) Temporal_French_list.get(0));
                checkbox2.setText((String) Temporal_French_list.get(1));
            }
        }
        Temporal_French_list.addAll(MainActivity.french_list);
    }

    public void handlePopUp()
    {
        score.setText(MainActivity.testScore +"/5");
        // Toast.makeText(this, "Test Completed \n Score : " + MainActivity.testScore, Toast.LENGTH_LONG).show();
        buildDialog();
    }

    private void buildDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Test Completed")
                .setMessage("Score : " + MainActivity.testScore +"/5" + "\n You are improving your french skills. " +
                        "\n Retake Test?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.testScore = 0;
                        MainActivity.testRound = 0;
                        Intent intent_test1 = new Intent(getBaseContext(), Test.class);
                        startActivity(intent_test1);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }


}
