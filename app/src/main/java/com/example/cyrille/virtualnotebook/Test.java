package com.example.cyrille.virtualnotebook;

import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;

/**
 * Created by dominiquekogue on 11/3/17.
 */

public class Test extends AppCompatActivity {

    CheckBox cb,cb2,cb3;

    protected void appTest(){



        cb = (CheckBox) findViewById(R.id.choice1);
        cb2 = (CheckBox) findViewById(R.id.choice2);
        cb3 = (CheckBox) findViewById(R.id.choice3);

    }
}
