package com.example.acookbookapp.ListRecipe;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acookbookapp.R;
import com.example.acookbookapp.SqLite.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    ListView listView;
    List list = new ArrayList();
    ArrayAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterR;
    TextView cat;
    private List<ListItem> listItems;
    SQLiteHelper myDb;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        //get recycler view
        recyclerView   = (RecyclerView)view.findViewById(R.id.recycler_view);
        cat = (TextView)view.findViewById(R.id.cat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        //get category from listclass activity
        String category=getArguments().getString("category");
        myDb = new SQLiteHelper(getActivity());
        listItems = new ArrayList<>();
//method used for testing purpose to check that recycler view works properly
//        for(int i =0; i<10; i++)
//        {
//            ListItem li = new ListItem(
//                    "head" + i,
//                    "lorem " + i
//            );
//
//            listItems.add(li);
//        }
        //check if an actual category is passed or if user asked to show all recipes.
    if (category.equals("All") ){

        cat.setText("All Recipes");
        //not category is passed get all recipes
        Cursor res = myDb.getAllDataRecipe();
        while (res.moveToNext()) {

            ListItem li = new ListItem(
                    res.getString(0),
                    res.getString(1),
                    res.getString(5),
                    res.getBlob(7),
                    res.getString(4)
            );

            listItems.add(li);
        }
    }else{
        cat.setText("Recipes for " + category);
        //get all recipes under selected category
        Cursor res = myDb.getAllRecipesByCategory(category);
        while (res.moveToNext()) {

            ListItem li = new ListItem(
                    res.getString(0),
                    res.getString(1),
                    res.getString(2),
                    res.getBlob(7),
                    res.getString(4)
            );

            listItems.add(li);
        }
    }
    //if recipes exists show them in the recycler view.
        if (listItems.size() != 0) {


            adapterR = new ListAdapter(listItems, view.getContext());

            recyclerView.setAdapter(adapterR);
        }

        return view;
    }
}
