package com.example.cyrille.virtualnotebook;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

//import com.example.cyrille.simplelistview.R;

public class Category extends AppCompatActivity {

    TextView category1, category2, category3, category4, category5, category6;
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        initInstances();
    }









    public void initInstances()
    {
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelp.disableShiftMode(bottomNavigationView);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.navigation_notebook:
                        //Do some thing here
                        Intent intent = new Intent(Category.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_add:
                        //Do some thing here
                        Intent intent_add = new Intent(Category.this, WordDetail.class);
                        startActivity(intent_add);
                        break;
                    case R.id.navigation_test:
                        //Do some thing here
                        Intent intent_test = new Intent(Category.this, Test.class);
                        startActivity(intent_test);
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

}


