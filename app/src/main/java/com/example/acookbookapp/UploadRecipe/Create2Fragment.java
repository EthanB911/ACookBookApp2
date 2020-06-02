package com.example.acookbookapp.UploadRecipe;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.acookbookapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Create2Fragment extends Fragment {

    EditText ingredients;

    FloatingActionButton addIngredient;

    ListView ingredientListView;

    ArrayList<String> ingredientList;

    ArrayAdapter<String> ingredientAdapter;
    View view;
    TextView remove;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create2, container, false);

        remove=view.findViewById(R.id.remove);

        ingredients = (EditText) view.findViewById(R.id.editText);//field to insert ingredient


        addIngredient = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);//button to enter current inserted ingredient


        ingredientListView = (ListView) view.findViewById(R.id.listView); //listview to show inserted ingredients

    //
        ingredientList = ((UploadActivity)getActivity()).ingredientListMain;//to get already inputted ingredients in case we are coming from fragment 3 and we already have inputtted ingredients
        //we need to populate the listview with the already inputted ingredients.


        ingredientAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, ingredientList);


        ingredientListView.setAdapter(ingredientAdapter);
//        ingredientListView.requestLayout();
//        ingredientAdapter.notifyDataSetChanged();
        ingredientListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                String curr = String.valueOf(position);
                String ing = parent.getItemAtPosition(position).toString();
                Toast toast= Toast.makeText(view.getContext(),
                        ing + " removed", Toast.LENGTH_SHORT);
                toast.show();
                ((UploadActivity) getActivity()).ingredientListMain.remove(position);
                ingredientListView.requestLayout();
                //ensuring that the listview changes immediately upon inserting a new ingredient
                ingredientAdapter.notifyDataSetChanged();
            }
        });
        if(((UploadActivity) getActivity()).ingredientListMain.size()!=0)
            remove.setVisibility(View.VISIBLE);
        else
            remove.setVisibility(View.GONE);
        onIngredientClick();
//        onInstructionClick();

        return view;
    }

            //method to add ingredient
    public void onIngredientClick(){
        addIngredient.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String result = ingredients.getText().toString();
                if(result.length() != 0) {
                    ((UploadActivity) getActivity()).ingredientListMain.add(result);
                    //ingredientList.add(result);
                    ingredientListView.requestLayout();
                    //ensuring that the listview changes immediately upon inserting a new ingredient
                    ingredientAdapter.notifyDataSetChanged();
                    ingredients.setText("");
                    ingredients.setHint("Add another ingredient");
                    Toast toast= Toast.makeText(view.getContext(),
                            result + " added", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    if(((UploadActivity) getActivity()).ingredientListMain.size()!=0)
                        remove.setVisibility(View.VISIBLE);
                    else
                        remove.setVisibility(View.GONE);
                }
            }

        });
    }







}
