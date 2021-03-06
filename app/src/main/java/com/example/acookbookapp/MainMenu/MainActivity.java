package com.example.acookbookapp.MainMenu;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.acookbookapp.R;
import com.example.acookbookapp.SqLite.SQLiteHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
//    public static final String IdKey = "idKey";
//    public static final String MyPREFERENCES = "MyPrefs" ;
//    SharedPreferences sharedpreferences;
SQLiteHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ViewPager2 viewPager2 = findViewById(R.id.fragment_container);
//        viewPager2.setAdapter(new MainPagerAdapter(this));
//        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//        createSession("2");

        //bottom navigation view is used to display the bottom toolbar where users can navigate between 3 fragments
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //seed data if no data is found
        //ideally move this method in db upon creation
        myDb = new SQLiteHelper(this);
        Cursor res = myDb.getAllDataRecipe();
        if (!(res.moveToFirst()) || res.getCount() ==0){
            //cursor is empty
            myDb.seed();
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }
    }

    //cases to check which fragment to direct user to
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_create:
                            selectedFragment = new CreateFragment();
                            break;
                        case R.id.nav_suggestions:
                            selectedFragment = new UserFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}
