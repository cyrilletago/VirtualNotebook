package com.example.cyrille.virtualnotebook;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Category extends AppCompatActivity implements View.OnClickListener{

    TextView categoryFruits, categoryAnimals, categoryEducation, categoryElectronics, categoryTransport, categoryOthers;
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        categoryFruits = ( TextView) findViewById(R.id.categoryFruits);
        categoryFruits.setOnClickListener(this);

        categoryAnimals = ( TextView) findViewById(R.id.categoryAnimal);
        categoryAnimals.setOnClickListener(this);

        categoryEducation = ( TextView) findViewById(R.id.categoryEdu);
        categoryEducation.setOnClickListener(this);

        categoryElectronics = ( TextView) findViewById(R.id.categoryElec);
        categoryElectronics.setOnClickListener(this);

        categoryTransport = ( TextView) findViewById(R.id.categoryTrans);
        categoryTransport.setOnClickListener(this);

        categoryOthers = ( TextView) findViewById(R.id.categoryOthers);
        categoryOthers.setOnClickListener(this);

        initInstances();
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.categoryFruits)
        {
            Intent intent = new Intent(getBaseContext(), CategoryChosen.class);
            intent.putExtra("CATEGORY_TO_BE_OPENED", "Fruit");
            startActivity(intent);
        }
        else  if(view.getId()==R.id.categoryAnimal)
        {
            Intent intent=new Intent(getBaseContext(), CategoryChosen.class);
            intent.putExtra("CATEGORY_TO_BE_OPENED", "Animal");
            startActivity(intent);
        }
        else  if(view.getId()==R.id.categoryEdu)
        {
            Intent intent=new Intent(getBaseContext(), CategoryChosen.class);
            intent.putExtra("CATEGORY_TO_BE_OPENED", "Education");
            startActivity(intent);
        }else  if(view.getId()==R.id.categoryElec)
        {
            Intent intent=new Intent(getBaseContext(), CategoryChosen.class);
            intent.putExtra("CATEGORY_TO_BE_OPENED", "Electronic");
            startActivity(intent);
        }else  if(view.getId()==R.id.categoryTrans)
        {
            Intent intent=new Intent(getBaseContext(), CategoryChosen.class);
            intent.putExtra("CATEGORY_TO_BE_OPENED", "Transport");
            startActivity(intent);
        }else  if(view.getId()==R.id.categoryOthers)
        {
            Intent intent=new Intent(getBaseContext(), CategoryChosen.class);
            intent.putExtra("CATEGORY_TO_BE_OPENED", "Other");
            startActivity(intent);
        }

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
                        Intent intent = new Intent(Category.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_add:
                        Intent intent_add = new Intent(Category.this, WordDetail.class);
                        startActivity(intent_add);
                        break;
                    case R.id.navigation_test:
                        Intent intent_test = new Intent(Category.this, Test.class);
                        startActivity(intent_test);
                        break;
                    case R.id.navigation_cathegory:
                        // add navigation drawer item onclick method here
                        break;
                }
                return false;
            }
        });

    }

}


