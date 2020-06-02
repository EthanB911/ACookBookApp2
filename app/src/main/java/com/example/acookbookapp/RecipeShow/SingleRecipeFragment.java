package com.example.acookbookapp.RecipeShow;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acookbookapp.MainMenu.MainActivity;
import com.example.acookbookapp.R;
import com.example.acookbookapp.SqLite.SQLiteHelper;
import com.example.acookbookapp.UploadRecipe.UploadActivity;

import java.util.ArrayList;
import java.util.List;

public class SingleRecipeFragment extends  Fragment{


    ListView listView;
    List list = new ArrayList();
    ArrayAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterR;
    private List<ListIngredient> listIngredients;
    private String ingredients;
    SQLiteHelper myDb;
    TextView title;
    ImageView viewImage;
    byte[] image;
    Button delete;
    String createdBy, userId, recipeId;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_single, container, false);
        title =(TextView) view.findViewById(R.id.title);
        viewImage = view.findViewById(R.id.view_image);
        delete =(Button)view.findViewById(R.id.delete) ;
        TextView instructions = view.findViewById(R.id.instructions);
        //obtaining id from an invisible field in layout
        String id=this.getArguments().getString("id").toString();


        SharedPreferences settings = getActivity().getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        userId = settings.getString("idKey", "");

       recyclerView = (RecyclerView)view.findViewById(R.id.ingredients_recycler);
       recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        myDb = new SQLiteHelper(getActivity());
        listIngredients = new ArrayList<>();


        //getting data of the selected recipe
        Cursor res = myDb.getSingleRecipe(id);

//        for(int i =0; i<10; i++)
//        {
//            ListIngredient li = new ListIngredient(
//                    "head" + i
//            );
//
//            listIngredients.add(li);
//        }
        //setting data to each view and all ingredients to a string
        while (res.moveToNext()) {
            recipeId = res.getString(0);
            title.setText(res.getString(1));
            ingredients = res.getString(2);
            instructions.setText(res.getString(3));
            image = res.getBlob(7);
            createdBy = res.getString(8);
        }

        if(createdBy.equals(userId))
        {
            delete.setVisibility(View.VISIBLE);
        }

        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                delete();
            }
        });

        if(image != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            viewImage.setImageBitmap(bitmap);
        }else{
            viewImage.setVisibility(View.GONE);
        }
            //converting string to an array by splitting for each comma
        String[] convertedRankArray = ingredients.split(",");
        for (String ingr : convertedRankArray) {
                    ListIngredient li = new ListIngredient(
                    ingr
                  );
            //adding each ingredient to the list
        listIngredients.add(li);
        }

    //passing list of ingrenients to the adapter for the recycler view.
        adapterR= new IngredientsAdapter(listIngredients, view.getContext());

        recyclerView.setAdapter(adapterR);

        return view;
    }

    public void delete()
    {
        Integer deletedRows = myDb.deleteData(recipeId);
        if(deletedRows > 0) {
            Toast.makeText(getActivity(), "Data Deleted", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
            getFragmentManager().popBackStack();
        }
        else
        Toast.makeText(getActivity(),"Data not Deleted",Toast.LENGTH_LONG).show();
    }
}

