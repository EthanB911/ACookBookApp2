package com.example.acookbookapp.MainMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.acookbookapp.ListRecipe.ListActivity;
import com.example.acookbookapp.R;

public class HomeFragment extends Fragment {
    //for now categories are hard coded as in there is not table in db that has list of categories.
    ImageButton romantic, deserts, salads, snacks, pizza, pasta, mexican, grill;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button showall = (Button)view.findViewById(R.id.button);
        romantic = (ImageButton)view.findViewById(R.id.romantic);
        deserts = (ImageButton)view.findViewById(R.id.desert);
        salads = (ImageButton)view.findViewById(R.id.salads);
        snacks = (ImageButton)view.findViewById(R.id.snacks);
        pizza = (ImageButton)view.findViewById(R.id.pizza);
        pasta = (ImageButton)view.findViewById(R.id.pasta);
        mexican = (ImageButton)view.findViewById(R.id.mexican);
        grill = (ImageButton)view.findViewById(R.id.grill);

        showall.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                viewAllRecipes(v);
            }
        });
        romantic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                viewRecipes("Romantic");
            }
        });
        deserts.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                viewRecipes("Desert");
            }
        });
        salads.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                viewRecipes("Salad");
            }
        });
        snacks.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                viewRecipes("Snack");
            }
        });
        pizza.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                viewRecipes("Pizza");
            }
        });
        pasta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                viewRecipes("Pasta");
            }
        });
        grill.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                viewRecipes("Grill");
            }
        });
        mexican.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                viewRecipes("Mexican");
            }
        });
        return view;
    }
//    public void movePage(View v)
//    {
//        Intent listAll = new Intent(getActivity(), ListActivity.class);
//        startActivity(listAll);
//    }
    //view all recipes in db
    public void viewAllRecipes(View v)
    {
        Intent single = new Intent(getActivity(), ListActivity.class);
        single.putExtra("category", "All");
        startActivity(single);
    }
    //view recipes by selected category
    public void viewRecipes(String category)
    {
        //here we will pas category to another activity
        Intent single = new Intent(getActivity(), ListActivity.class);
        single.putExtra("category", category);
        startActivity(single);
    }
}
