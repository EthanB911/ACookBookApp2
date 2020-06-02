package com.example.acookbookapp.ListRecipe;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.acookbookapp.ListRecipe.ListFragment;
import com.example.acookbookapp.R;

public class ListActivity extends AppCompatActivity {
    //main activity that holds lists of recipes that are either under a specific category or all recipes in db
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //to get category selected
        Intent intent = getIntent();
        //get category passed from home fragment
        String message = intent.getStringExtra("category");
        openListFragment(message);
    }

    public void  openListFragment(String category){
        //open fragment
        Fragment selectedFragment = new ListFragment();
        Bundle bundle=new Bundle();
        bundle.putString("category", category);
        //set category argument through bundle
        selectedFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();
    }
}
